package br.com.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.entity.PayrollDetail;

@Repository
@Transactional
public interface PayrollDetailRepository extends JpaRepository<PayrollDetail, Integer> {
	
	public void deleteByPayrollIdentifier(Integer payrollIdentifier);
}
