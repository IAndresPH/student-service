package com.studentmicroservice.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "students",
        uniqueConstraints = @UniqueConstraint(name = "uk_student_code", columnNames = "code"))
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String code;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer semester;

    @NotBlank
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String career;

    @NotBlank
    @Size(max = 20)
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @NotNull
    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    @Column(name = "average", nullable = false, precision = 2, scale = 1)
    private BigDecimal average;

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public Integer getSemester() {
        return semester;
    }

    public String getCareer() {
        return career;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }
}
