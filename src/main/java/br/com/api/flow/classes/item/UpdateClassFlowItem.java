package br.com.api.flow.classes.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.ClassMapper;
import br.com.api.dto.ClassDTO;
import br.com.api.dto.ScheduleDetailClassDTO;
import br.com.api.entity.repository.ClassRepository;

@Component
public class UpdateClassFlowItem {

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private ClassMapper classMapper;

	@Autowired
	private InsertScheduleDetailClassFlowItem insertScheduleDetailClassFlowItem;

	public ClassDTO update(ClassDTO clazz) {

//		return classMapper.toDTO(classRepository.save(classMapper.toEntity(clazz)));
		final ClassDTO classDTOAux = classMapper.toDTO(classRepository.save(classMapper.toEntity(clazz)));

		for (ScheduleDetailClassDTO scheduleDetailClassDTO : clazz.getScheduleDetailClassList()) {
			scheduleDetailClassDTO.setClazz(ClassDTO.builder().identifier(classDTOAux.getIdentifier()).build());
			scheduleDetailClassDTO = insertScheduleDetailClassFlowItem.insert(scheduleDetailClassDTO);
		}
		
		return classDTOAux;
	}
}
