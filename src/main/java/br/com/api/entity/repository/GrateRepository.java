package br.com.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.entity.Grate;

@Repository
@Transactional
public interface GrateRepository extends JpaRepository<Grate, Integer> {
	
}

