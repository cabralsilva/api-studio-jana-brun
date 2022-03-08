package br.com.api.flow.person;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.PersonDTO;
import br.com.api.exceptions.FindByFilterException;
import br.com.api.flow.person.item.CheckPersonFlowItem;

@Service
public class CheckPersonFlow {

	@Autowired
	private CheckPersonFlowItem checkIndividualFlowItem;

	@Transactional
	public PersonDTO execute(PersonDTO individualDTO) throws Exception {

		try {
			checkIndividual(individualDTO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}

		return individualDTO;
	}

	private void checkIndividual(PersonDTO individualDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {
		if (Objects.nonNull(individualDTO))
			individualDTO = checkIndividualFlowItem.checkIfExist(individualDTO);
	}
}
