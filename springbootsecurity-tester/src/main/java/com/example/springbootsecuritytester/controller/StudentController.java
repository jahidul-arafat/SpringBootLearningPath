package com.example.springbootsecuritytester.controller;

import com.example.springbootsecuritytester.domain.Student;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/api")
//@Tag(name = "Students", description = "Students API")
public class StudentController {
    private final List<Student> students;

    public StudentController() {
        students = new ArrayList<>();
        students.add(new Student(1L, "Alliy", "Aow"));
        students.add(new Student(2L, "Billy", "Bow"));
        students.add(new Student(3L, "Cilly", "Cow"));
    }
    // GET method to get a list of students
    @GetMapping("/students")
    public List<Student> getStudents() {
        return students;
    }

    // POST method to add a student
    // Only 'admin' user can create a student, rest of the users can't
    // we are adding method level security for Admin user
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }
}
