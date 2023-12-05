package fr.efrei.studentserver.web.rest;

import fr.efrei.studentserver.domain.Student;
import fr.efrei.studentserver.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class StudentResourceIT {
  @Autowired
  private StudentRepository studentRepository;

  @Test
  @Transactional
  void createStudent() throws Exception {
    int databaseSizeBeforeCreate = studentRepository.findAll().size();
    assertThat(databaseSizeBeforeCreate).isEqualTo(0);

    Student student = new Student();
    student.setName("Lisa");
    student.setAge(20);
    studentRepository.save(student);

    List<Student> students = studentRepository.findAll();
    assertThat(students).hasSize(databaseSizeBeforeCreate + 1);
  }

  @Test
  @Transactional
  void getStudentById() throws Exception {
    Student student = new Student();
    student.setName("Lisa");
    student.setAge(20);
    studentRepository.save(student);

    List<Student> students = studentRepository.findAll();
    assertThat(students).hasSize(1);

    Student studentToFind = students.get(0);
    Optional<Student> studentFound = studentRepository.findById(studentToFind.getId());
    assertThat(studentFound.isPresent()).isTrue();
    assertThat(studentFound.get().getName()).isEqualTo("Lisa");
  }

  @Test
  @Transactional
  void updateStudent() throws Exception {
    Student student = new Student();
    student.setName("Lisa");
    student.setAge(20);
    studentRepository.save(student);

    List<Student> students = studentRepository.findAll();
    assertThat(students).hasSize(1);

    Student studentToUpdate = students.get(0);
    studentToUpdate.setName("Lisa Simpson");
    studentRepository.save(studentToUpdate);

    List<Student> studentsAfterUpdate = studentRepository.findAll();
    assertThat(studentsAfterUpdate).hasSize(1);
    assertThat(studentsAfterUpdate.get(0).getName()).isEqualTo("Lisa Simpson");
  }

  @Test
  @Transactional
  void deleteStudent() throws Exception {
    Student student = new Student();
    student.setName("Lisa");
    student.setAge(20);
    studentRepository.save(student);

    List<Student> students = studentRepository.findAll();
    assertThat(students).hasSize(1);

    studentRepository.delete(student);

    List<Student> studentsAfterDelete = studentRepository.findAll();
    assertThat(studentsAfterDelete).hasSize(0);
  }
}
