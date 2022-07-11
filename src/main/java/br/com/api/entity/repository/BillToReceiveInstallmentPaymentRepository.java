package br.com.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.entity.BillToReceiveInstallmentPayment;

@Repository
public interface BillToReceiveInstallmentPaymentRepository extends JpaRepository<BillToReceiveInstallmentPayment, Integer> {
	
}

