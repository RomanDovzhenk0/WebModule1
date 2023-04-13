package com.infrastructure.rest;

import com.application.service.StudentService;
import com.infrastructure.dto.StudentDto;
import com.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {
   private final StudentService studentService;
   private final JwtTokenUtil jwtTokenUtil;

   @GetMapping("/students")
   @ResponseStatus(HttpStatus.OK)
   public List<StudentDto> getAllStudents( ) {
      return studentService.getAllStudents().stream().map(StudentDto::new).toList();
   }

   @GetMapping("/token")
   @ResponseStatus(HttpStatus.OK)
   public String getToken(@RequestParam String username) {
      return jwtTokenUtil.generateToken(studentService.findStudentByUsername(username));
   }

   @PostMapping("/students")
   @ResponseStatus(HttpStatus.CREATED)
   public StudentDto addStudent(@RequestBody StudentDto studentDto, @RequestHeader("Authorization") String token) {
      jwtTokenUtil.validateToken(token);
      return new StudentDto(studentService.saveStudent(studentDto.toStudent()));
   }
}
