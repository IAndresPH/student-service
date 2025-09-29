package com.studentmicroservice.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @NotBlank
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String subject;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal score;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer semester;

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public String getSubject() {
        return subject;
    }

    public BigDecimal getScore() {
        return score;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }
}
