package br.com.api.flow.address;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.AddressDTO;
import br.com.api.exceptions.FindByFilterException;
import br.com.api.flow.address.item.CheckAddressFlowItem;
import br.com.api.flow.city.item.CheckCityFlowItem;
import br.com.api.flow.country.item.CheckCountryFlowItem;
import br.com.api.flow.neighborhood.item.CheckNeighborhoodFlowItem;
import br.com.api.flow.state.item.CheckStateFlowItem;

@Service
public class CheckAddressFlow {

	@Autowired
	private CheckCountryFlowItem checkCountryFlowItem;
	@Autowired
	private CheckStateFlowItem checkStateFlowItem;
	@Autowired
	private CheckCityFlowItem checkCityFlowItem;
	@Autowired
	private CheckNeighborhoodFlowItem checkNeighborhoodFlowItem;
	@Autowired
	private CheckAddressFlowItem checkAddressFlowItem;

	@Transactional
	public AddressDTO execute(AddressDTO addressDTO) throws Exception {

		try {
			checkCountry(addressDTO);
			checkState(addressDTO);
			checkCity(addressDTO);
			checkNeighborhood(addressDTO);
			checkAddress(addressDTO);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}

		return addressDTO;
	}

	private void checkCountry(AddressDTO addressDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {
		if (Objects.isNull(addressDTO.getNeighborhood().getCity().getState().getCountry().getIdentifier()))
			addressDTO.getNeighborhood().getCity().getState().setCountry(
					checkCountryFlowItem.checkIfExist(addressDTO.getNeighborhood().getCity().getState().getCountry()));
	}

	private void checkState(AddressDTO addressDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {
		if (Objects.isNull(addressDTO.getNeighborhood().getCity().getState().getIdentifier()))
			addressDTO.getNeighborhood().getCity()
					.setState(checkStateFlowItem.checkIfExist(addressDTO.getNeighborhood().getCity().getState()));
	}

	private void checkCity(AddressDTO addressDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {
		if (Objects.isNull(addressDTO.getNeighborhood().getCity().getIdentifier()))
			addressDTO.getNeighborhood()
					.setCity(checkCityFlowItem.checkIfExist(addressDTO.getNeighborhood().getCity()));
	}

	private void checkNeighborhood(AddressDTO addressDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {
		if (Objects.isNull(addressDTO.getNeighborhood().getIdentifier()))
			addressDTO.setNeighborhood(checkNeighborhoodFlowItem.checkIfExist(addressDTO.getNeighborhood()));
	}

	private void checkAddress(AddressDTO addressDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {
		if (Objects.isNull(addressDTO.getIdentifier()))
			addressDTO = checkAddressFlowItem.checkIfExist(addressDTO);
	}
}
