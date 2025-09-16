package com.university.academic.student.Report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService implements IReportService {
    @Autowired
    private ReportRepository repository;

    @Override
    public List<Report> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Report> findById(int id){
        return repository.findById(id);
    }

    @Override
    public Report save(Report report){
        return repository.save(report);
    }

    @Override
    public void update(Report report, int id){
        Optional<Report> personOld = repository.findById(id);
        if(!personOld.isEmpty()){
            Report reportUpdate = personOld.get();
            repository.save(reportUpdate);
        } else {
            System.out.println("Invalid ID " + id);
        }
    }

    @Override
    public void delete(int id){
        repository.deleteById(id);
    }
}
