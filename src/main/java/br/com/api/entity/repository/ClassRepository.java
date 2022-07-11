package br.com.api.entity.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ClassRepository extends JpaRepository<br.com.api.entity.Class, Integer> {
	
	List<br.com.api.entity.Class> findByEndDateGreaterThan(Calendar target);
}

