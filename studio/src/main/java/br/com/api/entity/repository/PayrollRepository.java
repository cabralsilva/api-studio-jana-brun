package br.com.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.api.entity.Payroll;
import br.com.api.enums.PayrollStatusEnum;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Integer> {

	@Modifying
	@Query("UPDATE Payroll SET status = :status WHERE identifier = :identifier")
	public void update(PayrollStatusEnum status, Integer identifier);
}
