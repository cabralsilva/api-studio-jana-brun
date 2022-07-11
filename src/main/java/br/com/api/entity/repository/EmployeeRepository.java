package br.com.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.entity.Employee;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	@Modifying
	@Query("UPDATE Employee SET password = :password WHERE identifier = :identifier")
	public void updatePassword(String password, Integer identifier);
	
	Employee findByEmail(String email); 
}

