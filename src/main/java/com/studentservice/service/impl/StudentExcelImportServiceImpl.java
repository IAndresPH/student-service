package com.studentservice.service.impl;

import com.studentservice.dto.request.StudentRequestDTO;
import com.studentservice.dto.response.StudentResponseDTO;
import com.studentservice.enums.Career;
import com.studentservice.enums.Gender;
import com.studentservice.service.StudentExcelImportService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class StudentExcelImportServiceImpl implements StudentExcelImportService {

    private final IStudentService studentService;

    private static final Set<String> REQUIRED_HEADERS = Set.of(
            "first_name",
            "last_name",
            "email",
            "enrollment_number",
            "semester",
            "career"
    );

    private static final Map<String, String> HEADER_ALIASES;
    static {
        Map<String, String> m = new HashMap<>();
        m.put("first_name", "first_name");
        m.put("firstname", "first_name");
        m.put("first name", "first_name");
        m.put("name", "first_name");
        m.put("nombre", "first_name");

        m.put("last_name", "last_name");
        m.put("lastname", "last_name");
        m.put("last name", "last_name");
        m.put("apellido", "last_name");

        m.put("email", "email");
        m.put("correo", "email");

        m.put("gender", "gender");
        m.put("genero", "gender");

        m.put("birthdate", "birth_date");
        m.put("birth_date", "birth_date");
        m.put("date_of_birth", "birth_date");
        m.put("birth date", "birth_date");
        m.put("fecha_nacimiento", "birth_date");

        m.put("phone", "phone");
        m.put("telefono", "phone");
        m.put("tel", "phone");

        m.put("address", "address");
        m.put("direccion", "address");

        m.put("enrollment_number", "enrollment_number");
        m.put("matricula", "enrollment_number");
        m.put("code", "enrollment_number");
        m.put("codigo", "enrollment_number");

        m.put("semester", "semester");
        m.put("grade", "semester");
        m.put("semestre", "semester");
        m.put("grado", "semester");

        m.put("career", "career");
        m.put("specialty", "career");
        m.put("department", "career");
        m.put("carrera", "career");
        m.put("programa", "career");

        m.put("admissiondate", "admission_date");
        m.put("admission_date", "admission_date");
        m.put("admission date", "admission_date");
        m.put("enrollmentdate", "admission_date");
        m.put("enrollment_date", "admission_date");
        m.put("fecha_admision", "admission_date");
        m.put("fecha_matricula", "admission_date");

        m.put("average", "average");
        m.put("promedio", "average");
        m.put("gpa", "average");

        HEADER_ALIASES = Collections.unmodifiableMap(m);
    }

    private static final Pattern EMAIL_RX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);

    private static final boolean VALIDATE_AVERAGE_0_TO_5 = true;

    public StudentExcelImportServiceImpl(IStudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public List<StudentResponseDTO> importFromExcel(MultipartFile file) throws IOException {
        List<StudentResponseDTO> created = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getNumberOfSheets() > 0 ? workbook.getSheetAt(0) : null;
            if (sheet == null) {
                throw new IllegalArgumentException("El archivo no contiene hojas.");
            }

            Iterator<Row> rows = sheet.iterator();
            if (!rows.hasNext()) {
                throw new IllegalArgumentException("La hoja está vacía. Debe incluir una fila de encabezados.");
            }

            Row headerRow = rows.next();
            List<String> rawHeaders = new ArrayList<>();
            for (Cell c : headerRow) {
                rawHeaders.add(getCellValueAsString(c).trim().toLowerCase());
            }

            List<String> canonicalHeaders = rawHeaders.stream()
                    .map(h -> HEADER_ALIASES.getOrDefault(h, h))
                    .collect(Collectors.toList());

            Set<String> presentHeaders = new HashSet<>(canonicalHeaders);
            List<String> missing = REQUIRED_HEADERS.stream()
                    .filter(req -> !presentHeaders.contains(req))
                    .sorted()
                    .toList();
            if (!missing.isEmpty()) {
                throw new IllegalArgumentException(
                        "Formato inválido: faltan encabezados requeridos: " + String.join(", ", missing) +
                                ". Encabezados recibidos: " + String.join(", ", canonicalHeaders)
                );
            }

            List<String> unknown = new ArrayList<>();
            for (String h : canonicalHeaders) {
                if (!HEADER_ALIASES.containsValue(h) && !REQUIRED_HEADERS.contains(h)) {
                    unknown.add(h);
                }
            }

            DateTimeFormatter[] dateFormats = new DateTimeFormatter[]{
                    DateTimeFormatter.ISO_LOCAL_DATE,
                    DateTimeFormatter.ofPattern("d/MM/yyyy"),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
            };

            List<String> errors = new ArrayList<>();
            int createdCountBeforeValidation = 0;

            int rowNum = 1;
            while (rows.hasNext()) {
                Row row = rows.next();
                rowNum++;

                if (isRowEmpty(row)) continue;

                String firstName = null;
                String lastName = null;
                String email = null;
                LocalDate birthDate = null;
                Gender gender = null;
                String phone = null;
                String address = null;
                String enrollmentNumber = null;
                Integer semester = null;
                Career career = null;
                LocalDate admissionDate = null;
                BigDecimal average = null;

                for (int i = 0; i < canonicalHeaders.size(); i++) {
                    String key = canonicalHeaders.get(i);
                    Cell cell = row.getCell(i);
                    String value = getCellValueAsString(cell).trim();
                    if (value.isEmpty()) continue;

                    switch (key) {
                        case "first_name" -> firstName = value;
                        case "last_name" -> lastName = value;
                        case "email" -> email = value;
                        case "gender" -> {
                            String g = value.trim().toUpperCase();
                            if (g.startsWith("M")) gender = Gender.MASCULINO;
                            else if (g.startsWith("F")) gender = Gender.FEMENINO;
                            else gender = Gender.OTRO;
                        }
                        case "birth_date" -> birthDate = parseDate(value, dateFormats);
                        case "phone" -> phone = value;
                        case "address" -> address = value;
                        case "enrollment_number" -> enrollmentNumber = value;
                        case "semester" -> {
                            try {
                                semester = Integer.valueOf(value.replaceAll("[^0-9-]", ""));
                            } catch (Exception ignored) {
                            }
                        }
                        case "career" -> {
                            try {
                                String candidate = value.trim().toUpperCase()
                                        .replace(" ", "_")
                                        .replace("-", "_")
                                        .replace("\\", "_")
                                        .replace("/", "_");
                                career = Career.valueOf(candidate);
                            } catch (Exception ignored) {
                            }
                        }
                        case "admission_date" -> admissionDate = parseDate(value, dateFormats);
                        case "average" -> {
                            try {
                                String s = value.replaceAll("[^0-9,.-]", "");
                                if (s.contains(",") && s.matches(".*,\\d+$")) {
                                    s = s.replace(".", "").replace(",", ".");
                                } else {
                                    s = s.replace(",", "");
                                }
                                average = new BigDecimal(s);
                            } catch (Exception ignored) {
                            }
                        }
                        default -> {
                        }
                    }
                }

                List<String> rowErrors = new ArrayList<>();

                if (firstName == null || firstName.isBlank()) {
                    rowErrors.add("first_name es requerido");
                }
                if (lastName == null || lastName.isBlank()) {
                    rowErrors.add("last_name es requerido");
                }
                if (email == null || email.isBlank() || !EMAIL_RX.matcher(email).matches()) {
                    rowErrors.add("email es requerido y debe tener formato válido");
                }
                if (enrollmentNumber == null || enrollmentNumber.isBlank()) {
                    rowErrors.add("enrollment_number es requerido");
                }
                if (semester == null) {
                    rowErrors.add("semester es requerido y debe ser numérico");
                } else if (semester < 1 || semester > 20) {
                    rowErrors.add("semester debe estar entre 1 y 20");
                }
                if (career == null) {
                    String allowed = Arrays.stream(Career.values())
                            .map(Enum::name)
                            .collect(Collectors.joining(", "));
                    rowErrors.add("career inválido o ausente. Valores permitidos: " + allowed);
                }
                if (birthDate == null && hasHeader(canonicalHeaders, "birth_date")) {
                }
                if (admissionDate == null && hasHeader(canonicalHeaders, "admission_date")) {
                }
                if (average != null && VALIDATE_AVERAGE_0_TO_5) {
                    if (average.compareTo(BigDecimal.ZERO) < 0 || average.compareTo(BigDecimal.valueOf(5)) > 0) {
                        rowErrors.add("average debe estar entre 0 y 5");
                    }
                }

                if (!rowErrors.isEmpty()) {
                    errors.add("Fila " + rowNum + ": " + String.join("; ", rowErrors));
                    continue;
                }

                StudentRequestDTO dto;
                try {
                    dto = new StudentRequestDTO(
                            firstName,
                            lastName,
                            email,
                            birthDate,
                            gender,
                            phone,
                            address,
                            enrollmentNumber,
                            semester,
                            career,
                            admissionDate,
                            average
                    );
                } catch (Exception e) {
                    errors.add("Fila " + rowNum + ": error construyendo DTO (" + e.getMessage() + ")");
                    continue;
                }

                try {
                    StudentResponseDTO createdDto = studentService.create(dto);
                    created.add(createdDto);
                } catch (Exception e) {
                    errors.add("Fila " + rowNum + ": error al crear el estudiante (" + safeMsg(e) + ")");
                }
            }

            if (!errors.isEmpty()) {
                String head = created.isEmpty()
                        ? "El archivo no cumple el formato esperado."
                        : "Se registraron algunas filas, pero se encontraron errores de formato:";
                String detail = errors.stream().limit(50).collect(Collectors.joining("\n"));
                if (errors.size() > 50) {
                    detail += "\n... (" + (errors.size() - 50) + " errores más)";
                }
                throw new IllegalArgumentException(head + "\n" + detail);
            }

            if (created.isEmpty()) {
                throw new IllegalArgumentException("No se pudo registrar ninguna fila. Verifique la plantilla y los datos.");
            }
        }

        return created;
    }

    private static boolean hasHeader(List<String> canonicalHeaders, String key) {
        return canonicalHeaders.stream().anyMatch(h -> h.equalsIgnoreCase(key));
    }

    private static String safeMsg(Exception e) {
        String m = e.getMessage();
        if (m == null) return e.getClass().getSimpleName();
        m = m.replaceAll("\\s+", " ").trim();
        return m.length() > 300 ? m.substring(0, 300) + "..." : m;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                } else {
                    double d = cell.getNumericCellValue();
                    long lv = (long) d;
                    if (lv == d) return Long.toString(lv);
                    return Double.toString(d);
                }
            case FORMULA:
                try {
                    return cell.getStringCellValue();
                } catch (Exception e) {
                    try {
                        return Double.toString(cell.getNumericCellValue());
                    } catch (Exception ex) {
                        return cell.getCellFormula();
                    }
                }
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (Cell c : row) {
            if (c == null) continue;
            String v = getCellValueAsString(c);
            if (v != null && !v.trim().isEmpty()) return false;
        }
        return true;
    }

    private LocalDate parseDate(String value, DateTimeFormatter[] formats) {
        if (value == null || value.isBlank()) return null;
        for (DateTimeFormatter fmt : formats) {
            try {
                return LocalDate.parse(value, fmt);
            } catch (Exception ignored) {
            }
        }
        try {
            return LocalDate.parse(value);
        } catch (Exception ignored) {
        }
        return null;
    }
}
