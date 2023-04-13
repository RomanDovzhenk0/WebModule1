package com.application.service;

import com.domain.entity.Student;

import java.util.List;

public interface StudentService {
   List<Student> getAllStudents( );

   Student findStudentByUsername(String username);

   Student saveStudent(Student student);

   boolean existByUsername(String username);
}
