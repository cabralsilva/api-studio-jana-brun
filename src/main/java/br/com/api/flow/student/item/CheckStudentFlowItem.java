package br.com.api.flow.student.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.StudentDTO;
import br.com.api.entity.repository.StudentFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckStudentFlowItem {

	@Autowired
	private FindStudentByFilterFlowItem findStudentByFilterFlowItem;

	public StudentDTO checkIfExist(StudentDTO countryDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new StudentFilter();
		filter.setExample(countryDTO);
		filter.setPageable(Boolean.FALSE);
		
		final var existing = findStudentByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			countryDTO.setIdentifier(existing.get().getIdentifier());
		}
		return countryDTO;
	}
}
