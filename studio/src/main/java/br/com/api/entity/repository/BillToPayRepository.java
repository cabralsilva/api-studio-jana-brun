package br.com.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.api.entity.BillToPay;

@Repository
public interface BillToPayRepository extends JpaRepository<BillToPay, Integer> {

	@Modifying
	@Query("UPDATE BillToPay SET paymentCondition.identifier = :paymentConditionIdentifier WHERE identifier = :identifier")
	public void updatePaymentConditionByIdentifier(Integer paymentConditionIdentifier, Integer identifier);
}
