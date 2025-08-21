package com.shafayet.spring_batch.batchprocessing;

import com.shafayet.spring_batch.dto.StudentCsvDto;
import com.shafayet.spring_batch.dto.StudentDto;
import com.shafayet.spring_batch.entity.Student;
import com.shafayet.spring_batch.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class StudentItemProcessor implements ItemProcessor<StudentCsvDto, Student> {

    private static final Logger log = LoggerFactory.getLogger(StudentItemProcessor.class);

    @Autowired
    StudentService studentService;

    @Override
    public Student process(StudentCsvDto item) throws Exception {
        Student student = studentService.getStudentFromStudentCsbDto(item);
        log.info("Item to ({}) === Student ===> ({}) ", item, student);
        return student;
    }

}
