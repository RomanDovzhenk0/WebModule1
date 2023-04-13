package com.infrastructure.dto;

import com.domain.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
   private String username;
   private String name;
   private String surname;

   public StudentDto(Student student) {
      this.username = student.getUsername();
      this.name = student.getName();
      this.surname = student.getSurname();
   }

   public Student toStudent() {
      return new Student(username, name, surname);
   }
}
