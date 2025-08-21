package com.shafayet.spring_batch.controller;

import com.shafayet.spring_batch.dto.StudentDto;
import com.shafayet.spring_batch.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final JobLauncher jobLauncher;
    private final Job job;

    @PostMapping
    public ResponseEntity<StudentDto> save(@Valid @RequestBody StudentDto studentDto){
        StudentDto savedStudent = studentService.save(studentDto);
        return ResponseEntity.ok(savedStudent);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> hello(@PathVariable Long id){
        StudentDto studentDto = studentService.get(id);
        return ResponseEntity.ok(studentDto);
    }


    @PostMapping("/batch-data-processing")
    public String startJobManually() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("studentBatchStartTime", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(job, params);

        return "Student CSV Processing Job started!";
    }



}
