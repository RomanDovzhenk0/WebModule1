package com.application.service;

import com.domain.entity.Student;
import com.domain.repository.StudentRepository;
import com.exception.EntityNotFoundByUsernameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
   private static final String ENTITY_NOT_FOUND_BY_USERNAME_TEMPLATE = "Student was not found by username: ";
   private final StudentRepository studentRepository;

   public List<Student> getAllStudents() {
      return studentRepository.findAll();
   }

   public Student findStudentByUsername(String username) {
      return studentRepository.findStudentByUsername(username)
              .orElseThrow(( ) -> new EntityNotFoundByUsernameException(ENTITY_NOT_FOUND_BY_USERNAME_TEMPLATE + username));
   }

   public Student saveStudent(Student student) {
      return studentRepository.save(student);
   }

   @Override
   public boolean existByUsername(String username) {
      return studentRepository.existsByUsername(username);
   }
}