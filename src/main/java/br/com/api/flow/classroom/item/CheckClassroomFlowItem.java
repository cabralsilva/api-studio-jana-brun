package br.com.api.flow.classroom.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.ClassroomDTO;
import br.com.api.entity.repository.ClassroomFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckClassroomFlowItem {

	@Autowired
	private FindClassroomByFilterFlowItem findClassroomByFilterFlowItem;

	public ClassroomDTO checkIfExist(ClassroomDTO cityDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new ClassroomFilter();
		filter.setExample(cityDTO);
		filter.setPageable(Boolean.FALSE);

		final var existing = findClassroomByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			cityDTO.setIdentifier(existing.get().getIdentifier());
		}
		return cityDTO;
	}
}
