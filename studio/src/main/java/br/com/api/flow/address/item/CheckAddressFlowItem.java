package br.com.api.flow.address.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.AddressDTO;
import br.com.api.entity.repository.AddressFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckAddressFlowItem {

	@Autowired
	private FindAddressByFilterFlowItem findAddressByFilterFlowItem;
	@Autowired
	private InsertAddressFlowItem insertAddressFlowItem;

	public AddressDTO checkIfExist(AddressDTO addressDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new AddressFilter();
		filter.setExample(addressDTO);
		filter.setPageable(Boolean.FALSE);
		
		final var existing = findAddressByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			addressDTO.setIdentifier(existing.get().getIdentifier());
		} else {
			addressDTO.setIdentifier(insertAddressFlowItem.insert(addressDTO).getIdentifier());
		}
		return addressDTO;
	}
}
