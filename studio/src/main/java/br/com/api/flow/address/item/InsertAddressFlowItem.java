package br.com.api.flow.address.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.AddressMapper;
import br.com.api.dto.AddressDTO;
import br.com.api.entity.repository.AddressRepository;

@Component
public class InsertAddressFlowItem {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UpdateAddressFlowItem updateAddressFlowItem;

	@Autowired
	private AddressMapper addressMapper;

	public AddressDTO insert(@NonNull AddressDTO address) {

		if (Objects.nonNull(address.getIdentifier())) {
			return updateAddressFlowItem.update(address);
		}

		return addressMapper.toDTO(addressRepository.save(addressMapper.toEntity(address)));
	}
}
