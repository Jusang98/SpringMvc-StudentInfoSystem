package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentRestControllerTest {
    private MockMvc mockMvc;
    private StudentRepository studentRepository;
    private Student student;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);

        mockMvc = MockMvcBuilders.standaloneSetup(new StudentRestController(studentRepository))
                .build();
    }


    @Test
    void creatStudent() throws Exception {
        studentRepository.register("김상주", "rlatkdwn@gmail.com", 90, "잘했어요");
        mockMvc.perform(post("/students"))
                .andExpect(status().isOk());
    }

    @Test
    void viewStudent() throws Exception {
        studentRepository.getStudent(1);
        mockMvc.perform(post("/students/1"))
                .andExpect(status().isOk());
    }

    @Test
    void modifyStudent() throws Exception {
        studentRepository.modify(student);
        mockMvc.perform(post("/students/1"))
                .andExpect(status().isOk());
    }
}