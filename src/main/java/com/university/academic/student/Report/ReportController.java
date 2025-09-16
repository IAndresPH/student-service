package com.university.academic.student.Report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("report")
public class ReportController {
    @Autowired
    private IReportService service;

    @GetMapping()
    public List<Report> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Report> findById(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping()
    public Report save(@RequestBody Report report){
        return service.save(report);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Report report, @PathVariable int id){
        service.update(report, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
