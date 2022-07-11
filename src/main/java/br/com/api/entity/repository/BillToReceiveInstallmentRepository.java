package br.com.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.entity.BillToReceiveInstallment;
import br.com.api.enums.InstallmentStatusEnum;

@Repository
@Transactional
public interface BillToReceiveInstallmentRepository extends JpaRepository<BillToReceiveInstallment, Integer> {

	@Modifying
	@Query("UPDATE BillToReceiveInstallment SET status = :status WHERE identifier = :identifier")
	public void updateStatus(InstallmentStatusEnum status, Integer identifier);
}
