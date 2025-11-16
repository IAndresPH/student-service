package com.studentservice.controller;

import com.studentservice.dto.request.StudentRequestDTO;
import com.studentservice.dto.response.PaginatedResponse;
import com.studentservice.dto.response.StudentResponseDTO;
import com.studentservice.enums.Gender;
import com.studentservice.enums.Career;
import com.studentservice.service.impl.IStudentService;
import com.studentservice.service.impl.StudentImportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {

    private static final String BASE = "/api/v1/students";

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private IStudentService service;

    @MockitoBean
    private StudentImportService studentImportService;

    private static BigDecimal bd(String v) { return new BigDecimal(v); }

    private static StudentResponseDTO sample(Long id) {
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
                "STU-001",
                3,
                Career.SOFTWARE_ENGINEERING,
                LocalDate.of(2020, 1, 15),
                bd("4.5")
        );
    }

    @Test
    void getById_ok() throws Exception {
        when(service.getById(1L)).thenReturn(sample(1L));

        mvc.perform(get(BASE + "/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Ada"))
                .andExpect(jsonPath("$.code").value("STU-001"));
    }

    @Test
    void create_ok() throws Exception {
        when(service.create(any(StudentRequestDTO.class))).thenReturn(sample(2L));

        String body = """
            {
              "firstName":"Ada",
              "lastName":"Lovelace",
              "email":"ada.lovelace@example.com",
              "birthDate":"1990-12-10",
              "gender":"FEMENINO",
              "phone":"3001234567",
              "address":"Calle 123 #45-67",
              "code":"STU-001",
              "semester":3,
              "career":"SOFTWARE_ENGINEERING",
              "admissionDate":"2020-01-15",
              "average":4.5
            }
            """;

        mvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.firstName").value("Ada"))
                .andExpect(jsonPath("$.semester").value(3));
    }

    @Test
    void update_ok() throws Exception {
        when(service.update(eq(4L), any(StudentRequestDTO.class))).thenReturn(
                new StudentResponseDTO(
                        4L,"Grace","Hopper","grace.hopper@example.com",
                        LocalDate.of(1985,12,9),39, Gender.FEMENINO,"3017654321","Av. 10-20",
                        "STU-999",5,Career.COMPUTER_SCIENCE, LocalDate.of(2021,3,10), bd("4.7")
                )
        );

        String body = """
            {
              "firstName":"Grace",
              "lastName":"Hopper",
              "email":"grace.hopper@example.com",
              "birthDate":"1985-12-09",
              "gender":"FEMENINO",
              "phone":"3017654321",
              "address":"Av. 10-20",
              "code":"STU-999",
              "semester":5,
              "career":"COMPUTER_SCIENCE",
              "admissionDate":"2021-03-10",
              "average":4.7
            }
            """;

        mvc.perform(put(BASE + "/{id}", 4L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.firstName").value("Grace"))
                .andExpect(jsonPath("$.code").value("STU-999"));
    }

    @Test
    void delete_noContent() throws Exception {
        doNothing().when(service).delete(7L);

        mvc.perform(delete(BASE + "/{id}", 7L))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllPaginated_ok() throws Exception {
        PaginatedResponse<StudentResponseDTO> page = new PaginatedResponse<>(
                List.of(sample(6L)), 0, 1, 1, 10, false, false
        );
        when(service.getAllPaginated(0,10)).thenReturn(page);

        mvc.perform(get(BASE + "/paginated").param("page","0").param("size","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(6))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.currentPage").value(0))
                .andExpect(jsonPath("$.pageSize").value(10));
    }

    @Test
    void importStudents_ok() throws Exception {
        List<StudentResponseDTO> imported = List.of(
                sample(1L),
                sample(2L)
        );
        when(studentImportService.importStudents(any(List.class))).thenReturn(imported);

        String body = """
            [
              {
                "firstName":"Ada",
                "lastName":"Lovelace",
                "email":"ada.lovelace@example.com",
                "birthDate":"1990-12-10",
                "gender":"FEMENINO",
                "phone":"3001234567",
                "address":"Calle 123 #45-67",
                "code":"STU-001",
                "semester":3,
                "career":"SOFTWARE_ENGINEERING",
                "admissionDate":"2020-01-15",
                "average":4.5
              },
              {
                "firstName":"Ada",
                "lastName":"Lovelace",
                "email":"ada.lovelace@example.com",
                "birthDate":"1990-12-10",
                "gender":"FEMENINO",
                "phone":"3001234567",
                "address":"Calle 123 #45-67",
                "code":"STU-002",
                "semester":3,
                "career":"SOFTWARE_ENGINEERING",
                "admissionDate":"2020-01-15",
                "average":4.5
              }
            ]
            """;

        mvc.perform(post(BASE + "/import")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Ada"))
                .andExpect(jsonPath("$[1].id").value(2));
    }
}
