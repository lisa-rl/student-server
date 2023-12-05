package fr.efrei.studentserver.service;

import fr.efrei.studentserver.domain.Student;
import fr.efrei.studentserver.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
  
  public final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  // Retrieve all students from our database in a list of items
  public List<Student> findAll() {
    return studentRepository.findAll();
  }

  // Retrieve a single student from our database by id
  public Optional<Student> findById(Integer id) {
    return studentRepository.findById(id);
  }

  // Create a new student in our database
  public Student save(Student student) {
    return studentRepository.save(student);
  }

  // Delete a student from our database by id
  public Student deleteById(Integer id) {
    Optional<Student> student = studentRepository.findById(id);

    if(student.isPresent()) {
      studentRepository.deleteById(id);
      return student.get();
    } else {
      return null;
    }
  }
}
