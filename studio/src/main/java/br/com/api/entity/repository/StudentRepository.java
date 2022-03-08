package br.com.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.entity.Student;

@Repository
@Transactional
public interface StudentRepository extends JpaRepository<Student, Integer> {
	
}

