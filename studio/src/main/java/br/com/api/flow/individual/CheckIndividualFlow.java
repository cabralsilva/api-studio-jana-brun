package br.com.api.flow.individual;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.IndividualDTO;
import br.com.api.exceptions.FindByFilterException;
import br.com.api.flow.individual.item.CheckIndividualFlowItem;

@Service
public class CheckIndividualFlow {

	@Autowired
	private CheckIndividualFlowItem checkIndividualFlowItem;

	@Transactional
	public IndividualDTO execute(IndividualDTO individualDTO) throws Exception {

		try {
			checkIndividual(individualDTO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}

		return individualDTO;
	}

	private void checkIndividual(IndividualDTO individualDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {
		if (Objects.nonNull(individualDTO))
			individualDTO = checkIndividualFlowItem.checkIfExist(individualDTO);
	}
}
