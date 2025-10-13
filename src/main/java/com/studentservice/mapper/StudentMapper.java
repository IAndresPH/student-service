package com.studentservice.mapper;

import com.studentservice.entity.Student;
import com.studentservice.dto.request.StudentRequestDTO;
import com.studentservice.dto.response.StudentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.time.LocalDate;
import java.time.Period;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {
    Student toEntity(StudentRequestDTO dto);

    @Mapping(target = "age", expression = "java(calculateAge(student.getBirthDate()))")
    StudentResponseDTO toResponseDTO(Student student);

    default int calculateAge(LocalDate birthDate) {
        return birthDate != null ? Period.between(birthDate, LocalDate.now()).getYears() : 0;
    }
}