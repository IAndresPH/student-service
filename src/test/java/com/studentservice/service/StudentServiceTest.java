package com.studentservice.service;

import com.studentservice.dto.request.StudentRequestDTO;
import com.studentservice.dto.response.PaginatedResponse;
import com.studentservice.dto.response.StudentResponseDTO;
import com.studentservice.entity.Student;
import com.studentservice.exception.StudentNotFoundException;
import com.studentservice.mapper.StudentMapper;
import com.studentservice.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock private StudentRepository repository;
    @Mock private StudentMapper mapper;

    @InjectMocks private StudentService service;

    private StudentRequestDTO req;
    private Student entity;
    private StudentResponseDTO resp;

    private static BigDecimal bd(String v) { return new BigDecimal(v); }

    @BeforeEach
    void setup() {
        req = new StudentRequestDTO(
                "STU-001",3,"Ingeniería",
                LocalDate.of(2020,1,15), bd("4.5")
        );

        entity = new Student();
        entity.setId(1L);
        entity.setCode("STU-001");
        entity.setSemester(3);
        entity.setCareer("Ingeniería");
        entity.setAdmissionDate(LocalDate.of(2020,1,15));
        entity.setAverage(bd("4.5"));

        resp = new StudentResponseDTO(
                1L,"STU-001",3,"Ingeniería",
                LocalDate.of(2020,1,15), bd("4.5")
        );
    }

    @Test
    void create_ok() {
        when(mapper.toEntity(req)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(resp);

        StudentResponseDTO out = service.create(req);

        assertNotNull(out);
        verify(mapper).toEntity(req);
        verify(repository).save(entity);
        verify(mapper).toResponseDTO(entity);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void getById_ok() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(resp);

        StudentResponseDTO out = service.getById(1L);

        assertEquals(1L, out.id());
        verify(repository).findById(1L);
        verify(mapper).toResponseDTO(entity);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void getById_notFound_throws() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> service.getById(99L));
        verify(repository).findById(99L);
        verifyNoInteractions(mapper);
    }

    @Test
    void getAllPaginated_firstPage_noPrevNoNext() {
        Pageable pr = PageRequest.of(0,10);
        Page<Student> page = new PageImpl<>(List.of(entity), pr, 1);
        when(repository.findAll(pr)).thenReturn(page);
        when(mapper.toResponseDTO(entity)).thenReturn(resp);

        PaginatedResponse<StudentResponseDTO> out = service.getAllPaginated(0,10);

        assertEquals(1, out.getTotalElements());
        assertEquals(1, out.getTotalPages());
        assertFalse(out.isHasNext());
        assertFalse(out.isHasPrevious());
        assertEquals(0, out.getCurrentPage());
        assertEquals(10, out.getPageSize());
        verify(repository).findAll(pr);
        verify(mapper).toResponseDTO(entity);
    }

    @Test
    void getAllPaginated_middlePage_hasPrevAndNext() {
        Pageable pr = PageRequest.of(1,2);
        Page<Student> page = new PageImpl<>(List.of(entity, entity), pr, 5);
        when(repository.findAll(pr)).thenReturn(page);
        when(mapper.toResponseDTO(entity)).thenReturn(resp);

        PaginatedResponse<StudentResponseDTO> out = service.getAllPaginated(1,2);

        assertEquals(5, out.getTotalElements());
        assertEquals(3, out.getTotalPages());
        assertTrue(out.isHasNext());
        assertTrue(out.isHasPrevious());
        verify(repository).findAll(pr);
        verify(mapper, times(2)).toResponseDTO(entity);
    }

    @Test
    void update_ok_preservesId() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        Student mapped = new Student();
        mapped.setCode("STU-999");
        mapped.setSemester(5);
        mapped.setCareer("Sistemas");
        mapped.setAdmissionDate(LocalDate.of(2021,3,10));
        mapped.setAverage(bd("4.7"));

        Student saved = new Student();
        saved.setId(1L);
        saved.setCode("STU-999");
        saved.setSemester(5);
        saved.setCareer("Sistemas");
        saved.setAdmissionDate(LocalDate.of(2021,3,10));
        saved.setAverage(bd("4.7"));

        StudentResponseDTO updatedResp = new StudentResponseDTO(
                1L,"STU-999",5,"Sistemas",
                LocalDate.of(2021,3,10), bd("4.7")
        );

        when(mapper.toEntity(req)).thenReturn(mapped);
        when(repository.save(any(Student.class))).thenReturn(saved);
        when(mapper.toResponseDTO(saved)).thenReturn(updatedResp);

        StudentResponseDTO out = service.update(1L, req);

        assertEquals(1L, out.id());

        ArgumentCaptor<Student> cap = ArgumentCaptor.forClass(Student.class);
        verify(repository).findById(1L);
        verify(mapper).toEntity(req);
        verify(repository).save(cap.capture());
        verify(mapper).toResponseDTO(saved);

        assertEquals(1L, cap.getValue().getId());
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void update_notFound_throws() {
        when(repository.findById(123L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> service.update(123L, req));
        verify(repository).findById(123L);
        verify(repository, never()).save(any());
        verifyNoInteractions(mapper);
    }

    @Test
    void delete_ok() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void delete_notFound_throws() {
        when(repository.existsById(999L)).thenReturn(false);

        assertThrows(StudentNotFoundException.class, () -> service.delete(999L));
        verify(repository).existsById(999L);
        verify(repository, never()).deleteById(anyLong());
    }
}
