package com.studentmicroservice.Controller;

import com.studentmicroservice.Entity.Student;
import com.studentmicroservice.Service.StudentService;
import com.studentmicroservice.Service.GradeService;
import com.studentmicroservice.Controller.dto.GradesSummaryResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.studentmicroservice.Service.GradeService.SemesterAverage;

@RestController
@RequestMapping("/api/v1/students")
@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class StudentController {

    private final StudentService service;
    private final GradeService gradeService;

    public StudentController(StudentService service, GradeService gradeService) {
        this.service = service;
        this.gradeService = gradeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Student> create(@RequestBody Student student) {
        return ResponseEntity.status(201).body(service.create(student));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody Student student) {
        return ResponseEntity.ok(service.update(id, student));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint expected by frontend StudentGrades view
    @GetMapping("/{id}/grades/summary")
    public ResponseEntity<GradesSummaryResponse> gradesSummary(@PathVariable("id") Long studentId) {
        return ResponseEntity.ok(gradeService.summary(studentId));
    }

    // HU-107: progreso académico por semestre (ruta alternativa orientada a estudiante)
    @GetMapping("/{id}/grades/progress")
    public ResponseEntity<List<SemesterAverage>> gradesProgress(@PathVariable("id") Long studentId) {
        return ResponseEntity.ok(gradeService.progressBySemester(studentId));
    }
}
