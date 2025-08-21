package com.shafayet.spring_batch.service;

import com.shafayet.spring_batch.dao.StudentRepository;
import com.shafayet.spring_batch.dto.StudentCsvDto;
import com.shafayet.spring_batch.dto.StudentDto;
import com.shafayet.spring_batch.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentDto save(StudentDto studentDto){
        Student student = this.getStudentFromStudentDto(studentDto);
        Student savedStudent = studentRepository.saveAndFlush(student);
        return this.getStudentDtoFromStudent(savedStudent);
    }

    public StudentDto get(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        Student student = studentOptional.get();
        return this.getStudentDtoFromStudent(student);
    }

    public Student getStudentFromStudentDto(StudentDto studentDto){
        return Student.builder()
                .id(studentDto.id())
                .firstName(studentDto.firstName())
                .lastName(studentDto.lastName())
                .age(studentDto.age())
                .dateOfBirth(studentDto.dateOfBirth())
                .email(studentDto.email())
                .phoneNumber(studentDto.phoneNumber())
                .updated(studentDto.updated())
                .createdBy(studentDto.createdBy())
                .updatedBy(studentDto.updatedBy())
                .build();
    }

    public StudentDto getStudentDtoFromStudent(Student student){
        return StudentDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .age(student.getAge())
                .dateOfBirth(student.getDateOfBirth())
                .email(student.getEmail())
                .phoneNumber(student.getPhoneNumber())
                .created(student.getCreated())
                .updated(student.getUpdated())
                .createdBy(student.getCreatedBy())
                .updatedBy(student.getUpdatedBy())
                .build();
    }

    public Student getStudentFromStudentCsbDto(StudentCsvDto studentCsvDto){
        return Student.builder()
                .firstName(studentCsvDto.firstName())
                .lastName(studentCsvDto.lastName())
                .age(studentCsvDto.age())
                .dateOfBirth(studentCsvDto.dateOfBirth())
                .email(studentCsvDto.email())
                .phoneNumber(studentCsvDto.phoneNumber())
                .build();
    }

}
