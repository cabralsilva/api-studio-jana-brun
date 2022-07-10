package br.com.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.api.entity.BillToPayInstallment;
import br.com.api.enums.InstallmentStatusEnum;

@Repository
public interface BillToPayInstallmentRepository extends JpaRepository<BillToPayInstallment, Integer> {

	@Modifying
	@Query("UPDATE BillToPayInstallment SET status = :status WHERE identifier = :identifier")
	public void updateStatus(InstallmentStatusEnum status, Integer identifier);
}

