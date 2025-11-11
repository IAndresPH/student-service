package com.studentservice.service;

import   com.studentservice.dto.response.StudentResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentExcelImportService {
    List<StudentResponseDTO> importFromExcel(MultipartFile file) throws IOException;
}

