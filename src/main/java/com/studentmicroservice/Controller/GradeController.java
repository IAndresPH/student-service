package com.studentmicroservice.Controller;

import com.studentmicroservice.Controller.dto.GradeRequest;
import com.studentmicroservice.Controller.dto.GradesSummaryResponse;
import com.studentmicroservice.Entity.Grade;
import com.studentmicroservice.Service.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grades")
@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class GradeController {

    private final GradeService service;

    public GradeController(GradeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Grade> create(@RequestBody GradeRequest request) {
        return ResponseEntity.status(201).body(service.create(request));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Grade>> listByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(service.listByStudent(studentId));
    }

    @GetMapping("/student/{studentId}/summary")
    public ResponseEntity<GradesSummaryResponse> summary(@PathVariable Long studentId) {
        return ResponseEntity.ok(service.summary(studentId));
    }

    // HU-107: progreso académico por semestre
    @GetMapping("/student/{studentId}/progress")
    public ResponseEntity<List<com.studentmicroservice.Service.GradeService.SemesterAverage>> progress(@PathVariable Long studentId) {
        return ResponseEntity.ok(service.progressBySemester(studentId));
    }
}
