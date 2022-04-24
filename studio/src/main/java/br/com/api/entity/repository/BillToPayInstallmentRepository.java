package br.com.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.entity.BillToPayInstallment;

@Repository
public interface BillToPayInstallmentRepository extends JpaRepository<BillToPayInstallment, Integer> {
	
}

