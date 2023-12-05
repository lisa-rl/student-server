package fr.efrei.studentserver.web.rest;

import fr.efrei.studentserver.domain.Student;
import fr.efrei.studentserver.service.StudentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")

public class StudentResource {
  
  public final StudentService studentService;

  public StudentResource(StudentService studentService) {
    this.studentService = studentService;
  }

  // READ - Recover all students
  @GetMapping("/students")
  public List<Student> getAllStudents() {
    return studentService.findAll();
  }

  // READ - Retrieve a single student selected by id
  @GetMapping("/students/{id}")
  public ResponseEntity<?> getStudentById(@PathVariable Integer id) {
    Optional<Student> student = studentService.findById(id);

    if(student.isPresent()) {
      return ResponseEntity.ok(student.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found with ID: " + id);
    }
  }

  // POST - Create a new student
  @PostMapping("/students")
  public ResponseEntity<?> createStudent(
    @RequestParam(name= "name") String name, 
    @RequestParam(name= "age") Integer age
  ) {
    // Create a new student
    Student student = new Student();
    student.setName(name);
    student.setAge(age);

    // Save the new student in our database
    Student newStudent = studentService.save(student);

    if (newStudent != null) {
      // Return all students with the new one included
      List<Student> students = studentService.findAll();
      return ResponseEntity.status(HttpStatus.CREATED).body(students);      
    } else {
      // Return HTTP response when the request fails
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student not created");
    }
  }

  // PUT - Update a student
  @PutMapping("/students/{id}")
  public ResponseEntity<?> updateStudent(
    @PathVariable(name= "id") Integer id, 
    @RequestParam(name= "name") String name, 
    @RequestParam(name= "age") Integer age
  ) {
    // Retrieve the student to update
    Optional<Student> studentToUpdate = studentService.findById(id);

    if(studentToUpdate.isPresent()) {
      // Update the student
      Student student = studentToUpdate.get();
      if (name != null) { student.setName(name); }
      if (age != null) { student.setAge(age); }

      // Save the updated student in our database
      Student updatedStudent = studentService.save(student);

      if (updatedStudent != null) {
        // Return all students with the updated one included
        List<Student> allStudents = studentService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(allStudents);      
      } else {
        // Return HTTP response when the request fails
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student not updated");
      }
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found with ID: " + id);
    }
  }

  // DELETE - Delete a student
  @DeleteMapping("/students/{id}")
  public ResponseEntity<?> deleteStudentById(@PathVariable Integer id) {
    Optional<Student> studentToDelete = studentService.findById(id);

    if (studentToDelete.isPresent()) {
      studentService.deleteById(id);
      List<Student> students = studentService.findAll();
      return ResponseEntity.status(HttpStatus.OK).body(students);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found with ID: " + id);
    }
  }
}
