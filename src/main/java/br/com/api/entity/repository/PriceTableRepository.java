package br.com.api.entity.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.entity.PriceTable;

@Repository
@Transactional
public interface PriceTableRepository extends JpaRepository<PriceTable, Integer> {

	PriceTable findFirstByInitDateTimeLessThanEqualAndEndDateTimeGreaterThanEqualOrderByCreationDateTimeDesc(LocalDateTime initDateTime,
			LocalDateTime endDateTime);
}
