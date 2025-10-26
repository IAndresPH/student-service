package com.studentservice.mapper;

import com.studentservice.entity.Student;
import com.studentservice.dto.request.StudentRequestDTO;
import com.studentservice.dto.response.StudentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {
    Student toEntity(StudentRequestDTO dto);
    StudentResponseDTO toResponseDTO(Student student);
}