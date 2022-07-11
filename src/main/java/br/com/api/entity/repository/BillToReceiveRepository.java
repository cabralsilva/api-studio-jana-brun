package br.com.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.api.entity.BillToReceive;

@Repository
public interface BillToReceiveRepository extends JpaRepository<BillToReceive, Integer> {

	@Modifying
	@Query("UPDATE BillToReceive SET paymentCondition.identifier = :paymentConditionIdentifier WHERE identifier = :identifier")
	public void updatePaymentConditionByIdentifier(Integer paymentConditionIdentifier, Integer identifier);
}
