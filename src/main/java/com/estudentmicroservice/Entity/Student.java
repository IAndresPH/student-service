package com.estudentmicroservice.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

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

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal gpa;

    public Long getId() { return id; }
    public String getCode() { return code; }
    public Integer getSemester() { return semester; }
    public String getCareer() { return career; }
    public BigDecimal getGpa() { return gpa; }

    public void setId(Long id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setSemester(Integer semester) { this.semester = semester; }
    public void setCareer(String career) { this.career = career; }
    public void setGpa(BigDecimal gpa) { this.gpa = gpa; }
}
