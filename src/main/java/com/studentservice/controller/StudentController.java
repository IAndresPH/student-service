package com.studentservice.controller;

import com.studentservice.dto.request.StudentRequestDTO;
import com.studentservice.dto.response.PaginatedResponse;
import com.studentservice.dto.response.StudentResponseDTO;
import com.studentservice.service.impl.IStudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.studentservice.service.impl.StudentImportService;

import java.util.List;

import static com.studentservice.utils.ApiPaths.STUDENTS;

@RestController
@RequestMapping(STUDENTS)
public class StudentController {

    private final IStudentService service;
    private final StudentImportService importService;

    public StudentController(IStudentService service, StudentImportService importService) {
        this.service = service;
        this.importService = importService;
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> create(@RequestBody StudentRequestDTO dto) {
        StudentResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable Long id) {
        StudentResponseDTO student = service.getById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/paginated")
    public ResponseEntity<PaginatedResponse<StudentResponseDTO>> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginatedResponse<StudentResponseDTO> response = service.getAllPaginated(page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> update(@PathVariable Long id, @RequestBody StudentRequestDTO dto) {
        StudentResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/import", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentResponseDTO>> importStudents(@RequestBody List<StudentRequestDTO> students) {
        List<StudentResponseDTO> result = importService.importStudents(students);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}