package com.studentservice.service.impl;

import com.studentservice.dto.request.StudentRequestDTO;
import com.studentservice.dto.response.PaginatedResponse;
import com.studentservice.dto.response.StudentResponseDTO;

public interface IStudentService {
    StudentResponseDTO create(StudentRequestDTO studentRequestDTO);
    StudentResponseDTO getById(Long id);
    PaginatedResponse<StudentResponseDTO> getAllPaginated(int page, int size);
    StudentResponseDTO update(Long id, StudentRequestDTO studentRequestDTO);
    void delete(Long id);
}