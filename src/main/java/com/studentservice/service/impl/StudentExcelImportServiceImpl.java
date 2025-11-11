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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class StudentExcelImportServiceImpl implements StudentExcelImportService {

    private final IStudentService studentService;

    public StudentExcelImportServiceImpl(IStudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public List<StudentResponseDTO> importFromExcel(MultipartFile file) throws IOException {
        List<StudentResponseDTO> created = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getNumberOfSheets() > 0 ? workbook.getSheetAt(0) : null;
            if (sheet == null) return created;

            Iterator<Row> rows = sheet.iterator();
            if (!rows.hasNext()) return created;

            // leer encabezado
            Row header = rows.next();
            List<String> headers = new ArrayList<>();
            for (Cell c : header) {
                headers.add(getCellValueAsString(c).trim().toLowerCase());
            }

            DateTimeFormatter[] dateFormats = new DateTimeFormatter[]{
                    DateTimeFormatter.ISO_LOCAL_DATE,
                    DateTimeFormatter.ofPattern("d/MM/yyyy"),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
            };

            while (rows.hasNext()) {
                Row row = rows.next();
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

                for (int i = 0; i < headers.size(); i++) {
                    String key = headers.get(i);
                    Cell cell = row.getCell(i);
                    String value = getCellValueAsString(cell).trim();
                    if (value.isEmpty()) continue;

                    switch (key) {
                        case "first_name":
                        case "firstname":
                        case "first name":
                        case "name":
                            firstName = value;
                            break;
                        case "last_name":
                        case "lastname":
                        case "last name":
                            lastName = value;
                            break;
                        case "email":
                            email = value;
                            break;
                        case "gender":
                            String g = value.toUpperCase();
                            if (g.contains("M")) gender = Gender.MASCULINO;
                            else if (g.contains("F")) gender = Gender.FEMENINO;
                            else gender = Gender.OTRO;
                            break;
                        case "birthdate":
                        case "birth_date":
                        case "date_of_birth":
                        case "birth date":
                            birthDate = parseDate(value, dateFormats);
                            break;
                        case "phone":
                        case "telefono":
                        case "tel":
                            phone = value;
                            break;
                        case "address":
                        case "direccion":
                            address = value;
                            break;
                        case "enrollment_number":
                        case "matricula":
                        case "code":
                            enrollmentNumber = value;
                            break;
                        case "grade":
                        case "semester":
                            try {
                                semester = Integer.valueOf(value.replaceAll("[^0-9-]", ""));
                            } catch (Exception ignored) {
                            }
                            break;
                        case "career":
                        case "specialty":
                        case "department":
                            try {
                                // Normalizar a formato de enum: reemplazar espacios y guiones por _ y barras por _
                                String candidate = value.trim().toUpperCase()
                                        .replace(" ", "_")
                                        .replace("-", "_")
                                        .replace("\\\\", "_");
                                 career = Career.valueOf(candidate);
                            } catch (Exception ignored) {
                            }
                            break;
                        case "admissiondate":
                        case "admission_date":
                        case "admission date":
                        case "enrollmentdate":
                        case "enrollment_date":
                            admissionDate = parseDate(value, dateFormats);
                            break;
                        case "average":
                        case "promedio":
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
                            break;
                        default:
                            break;
                    }
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
                            semester != null ? semester : 0,
                            career,
                            admissionDate,
                            average
                    );
                } catch (Exception e) {

                    continue;
                }

                try {
                    StudentResponseDTO createdDto = studentService.create(dto);
                    created.add(createdDto);
                } catch (Exception e) {

                }
            }
        }

        return created;
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
                    return cell.getCellFormula();
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
