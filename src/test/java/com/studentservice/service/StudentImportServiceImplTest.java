package com.studentservice.service;

import com.studentservice.dto.request.StudentRequestDTO;
import com.studentservice.dto.response.StudentResponseDTO;
import com.studentservice.enums.Career;
import com.studentservice.enums.Gender;
import com.studentservice.service.impl.IStudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentImportServiceImplTest {

    @Mock
    private IStudentService studentService;

    private StudentImportServiceImpl importService;

    @BeforeEach
    void setUp() {
        importService = new StudentImportServiceImpl(studentService);
    }

    private static StudentRequestDTO mockRequest() {
        return new StudentRequestDTO(
                "Ada",
                "Lovelace",
                "ada@example.com",
                LocalDate.of(1990, 12, 10),
                Gender.FEMENINO,
                "3001234567",
                "Calle 123 #45-67",
                "STU-001",
                3,
                Career.SOFTWARE_ENGINEERING,
                LocalDate.of(2020, 1, 15),
                new BigDecimal("4.5")
        );
    }

    private static StudentResponseDTO sampleResponse(Long id) {
        return new StudentResponseDTO(
                id,
                "Ada",
                "Lovelace",
                "ada.lovelace@example.com",
                LocalDate.of(1990, 12, 10),
                34,
                Gender.FEMENINO,
                "3001234567",
                "Calle 123 #45-67",
                "STU-00" + id,
                3,
                Career.SOFTWARE_ENGINEERING,
                LocalDate.of(2020, 1, 15),
                new BigDecimal("4.5")
        );
    }

    @Test
    void importStudents_allCreated_ok() {
        StudentRequestDTO dto1 = mockRequest();
        StudentRequestDTO dto2 = mockRequest();

        StudentResponseDTO resp1 = sampleResponse(1L);
        StudentResponseDTO resp2 = sampleResponse(2L);

        when(studentService.create(any(StudentRequestDTO.class)))
                .thenReturn(resp1, resp2);

        List<StudentResponseDTO> result = importService.importStudents(List.of(dto1, dto2));

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals(2L, result.get(1).id());

        verify(studentService, times(2)).create(any(StudentRequestDTO.class));
    }

    @Test
    void importStudents_oneFails_otherSucceeds() {
        StudentRequestDTO dto1 = mockRequest();
        StudentRequestDTO dto2 = mockRequest();

        StudentResponseDTO resp1 = sampleResponse(10L);

        when(studentService.create(any(StudentRequestDTO.class)))
                .thenReturn(resp1)
                .thenThrow(new RuntimeException("Fallo al crear"));

        List<StudentResponseDTO> result = importService.importStudents(List.of(dto1, dto2));

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).id());

        verify(studentService, times(2)).create(any(StudentRequestDTO.class));
    }
}
