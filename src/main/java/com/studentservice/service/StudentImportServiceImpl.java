package com.studentservice.service;

import com.studentservice.dto.request.StudentRequestDTO;
import com.studentservice.dto.response.StudentResponseDTO;
import com.studentservice.service.impl.IStudentService;
import com.studentservice.service.impl.StudentImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentImportServiceImpl implements StudentImportService {

    private static final Logger log = LoggerFactory.getLogger(StudentImportServiceImpl.class);

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
                log.error("Error creando estudiante durante importación. Payload: {}. Causa: {}",
                        dto, e.getMessage(), e);
            }
        }

        return created;
    }
}
