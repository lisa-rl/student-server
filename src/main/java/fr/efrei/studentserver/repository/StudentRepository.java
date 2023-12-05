package fr.efrei.studentserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.efrei.studentserver.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
  
}
