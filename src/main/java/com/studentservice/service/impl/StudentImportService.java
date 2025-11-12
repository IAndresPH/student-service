package com.studentservice.service.impl;

import com.studentservice.dto.request.StudentRequestDTO;
import com.studentservice.dto.response.StudentResponseDTO;

import java.util.List;

public interface StudentImportService {
    List<StudentResponseDTO> importStudents(List<StudentRequestDTO> students);
}