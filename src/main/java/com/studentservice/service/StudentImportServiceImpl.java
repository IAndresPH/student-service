package com.studentservice.service;

import com.studentservice.dto.request.StudentRequestDTO;
import com.studentservice.dto.response.StudentResponseDTO;
import com.studentservice.service.impl.IStudentService;
import com.studentservice.service.impl.StudentImportService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentImportServiceImpl implements StudentImportService {

    private final IStudentService studentService;

    public StudentImportServiceImpl(IStudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public List<StudentResponseDTO> importStudents(List<StudentRequestDTO> students) {
        List<StudentResponseDTO> created = new ArrayList<>();

        for (StudentRequestDTO dto : students) {
            try {
                StudentResponseDTO createdDto = studentService.create(dto);
                created.add(createdDto);
            } catch (Exception e) {
                System.err.println("Error creando estudiante: " + " → " + e.getMessage());
            }
        }

        return created;
    }
}