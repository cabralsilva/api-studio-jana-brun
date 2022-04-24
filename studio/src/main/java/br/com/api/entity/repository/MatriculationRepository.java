package br.com.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.entity.Matriculation;
import br.com.api.enums.MatriculationStatusEnum;

@Repository
@Transactional
public interface MatriculationRepository extends JpaRepository<Matriculation, Integer> {
	
	@Modifying
	@Query("UPDATE Matriculation SET status = :status WHERE identifier = :identifier")
	public void updateStatusByIdentifier(MatriculationStatusEnum status, Integer identifier);
}

