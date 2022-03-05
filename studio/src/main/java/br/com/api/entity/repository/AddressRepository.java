package br.com.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.entity.Address;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<Address, Integer> {
	
}

