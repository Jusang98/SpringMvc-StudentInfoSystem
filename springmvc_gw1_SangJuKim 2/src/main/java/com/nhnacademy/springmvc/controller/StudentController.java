package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Student;

import com.nhnacademy.springmvc.domain.StudentRegisterRequest;
import com.nhnacademy.springmvc.exception.StudentNotFoundException;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import com.nhnacademy.springmvc.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @ModelAttribute("student")
    public Student getStudent(@PathVariable("studentId") Long studentId) {
        Student student = studentRepository.getStudent(studentId);
        if (Objects.isNull(student)) {
            throw new StudentNotFoundException();
        }
        return student;
    }

    @GetMapping("/{studentId}")
    public String viewStudent(@ModelAttribute("student") Student student, Model model) {
        model.addAttribute("student", student);
        return "thymeleaf/studentView";
    }

    @GetMapping(value = "/{studentId}", params = {"hideScore"})
    public String hideViewStudent(@RequestParam("hideScore") String hideScore,
                                  @ModelAttribute("student") Student student, Model model) {
        model.addAttribute("student", student);
        return "thymeleaf/studentView";
    }

    @GetMapping("/{studentId}/modify")
    public String studentModifyForm(@ModelAttribute Student student, ModelMap modelMap) {
        modelMap.put("student", student);
        return "thymeleaf/studentModify";
    }

    @PostMapping("/{studentId}/modify")
    public String modifyStudent(@ModelAttribute Student student,
                                @Valid @ModelAttribute StudentRegisterRequest studentRegisterRequest,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        student.setName(studentRegisterRequest.getName());
        student.setEmail(studentRegisterRequest.getEmail());
        student.setScore(studentRegisterRequest.getScore());
        student.setComment(studentRegisterRequest.getComment());

        studentRepository.modify(student);

        model.addAttribute("student", student);
        return "thymeleaf/studentView";
    }

}
