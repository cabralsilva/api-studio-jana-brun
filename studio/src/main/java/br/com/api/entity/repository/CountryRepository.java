package br.com.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.entity.Country;

@Repository
@Transactional
public interface CountryRepository extends JpaRepository<Country, Integer> {
	
}

