package com.studentservice.service.impl;

import com.studentservice.dto.request.StudentRequestDTO;
import com.studentservice.dto.response.StudentResponseDTO;
import java.util.List;

public interface IStudentService {
    StudentResponseDTO create(StudentRequestDTO studentRequestDTO);
    StudentResponseDTO getById(Long id);
    List<StudentResponseDTO> getAll();
    StudentResponseDTO update(Long id, StudentRequestDTO studentRequestDTO);
    void delete(Long id);
}