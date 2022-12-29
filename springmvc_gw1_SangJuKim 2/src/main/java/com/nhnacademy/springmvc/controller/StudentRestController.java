package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.domain.StudentRegisterRequest;
import com.nhnacademy.springmvc.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentRestController {

    private final StudentRepository studentRepository;


    public StudentRestController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;

    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Student creatStudent(@RequestBody StudentRegisterRequest student) {
        return studentRepository.register(student.getName(), student.getEmail(), student.getScore(), student.getComment());
    }

    @GetMapping("/{studentId}")
    public Student viewStudent(@PathVariable("studentId") long studentId) {
        return studentRepository.getStudent(studentId);
    }

    @PutMapping("/{studentId}")
    public Student modifyStudent(@PathVariable("studentId") long studentId, @RequestBody Student student) {
        studentRepository.modify(student);
        return studentRepository.getStudent(studentId);
    }
}
