package com.studentmicroservice.Repository;

import com.studentmicroservice.Entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findByStudentId(Long studentId);

    @Query("select g.semester as semester, avg(g.score) as average " +
           "from Grade g where g.student.id = :studentId group by g.semester order by g.semester")
    List<Object[]> averageBySemester(@Param("studentId") Long studentId);
}
