package com.studentservice.service;

import com.studentservice.dto.request.StudentRequestDTO;
import com.studentservice.dto.response.StudentResponseDTO;
import com.studentservice.entity.Student;
import com.studentservice.exception.StudentNotFoundException;
import com.studentservice.mapper.StudentMapper;
import com.studentservice.repository.StudentRepository;
import com.studentservice.service.impl.IStudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService implements IStudentService {

    private final StudentRepository repository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository repository, StudentMapper studentMapper) {
        this.repository = repository;
        this.studentMapper = studentMapper;
    }

    @Override
    public StudentResponseDTO create(StudentRequestDTO studentRequestDTO) {
        Student student = studentMapper.toEntity(studentRequestDTO);
        Student saved = repository.save(student);
        return studentMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDTO getById(Long id) {
        Student student = repository.findById(id)
                .orElseThrow(StudentNotFoundException::new);
        return studentMapper.toResponseDTO(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(studentMapper::toResponseDTO)
                .toList();
    }

    @Override
    public StudentResponseDTO update(Long id, StudentRequestDTO dto) {
        Student existing = repository.findById(id)
                .orElseThrow(StudentNotFoundException::new);

        Student updated = studentMapper.toEntity(dto);
        updated.setId(existing.getId());

        Student saved = repository.save(updated);
        return studentMapper.toResponseDTO(saved);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new StudentNotFoundException();
        }
        repository.deleteById(id);
    }
}