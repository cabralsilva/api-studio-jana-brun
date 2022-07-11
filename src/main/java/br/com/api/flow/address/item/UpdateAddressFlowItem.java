package br.com.api.flow.address.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.AddressMapper;
import br.com.api.dto.AddressDTO;
import br.com.api.entity.repository.AddressRepository;

@Component
public class UpdateAddressFlowItem {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private AddressMapper addressMapper;

	public AddressDTO update(AddressDTO address) {

		return addressMapper.toDTO(addressRepository.save(addressMapper.toEntity(address)));
	}
}
