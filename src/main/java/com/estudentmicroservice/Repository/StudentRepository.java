package com.estudentmicroservice.Repository;

import com.estudentmicroservice.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByCode(String code);
    Optional<Student> findByCode(String code);
}
