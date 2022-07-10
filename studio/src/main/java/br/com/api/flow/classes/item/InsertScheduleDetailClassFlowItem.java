package br.com.api.flow.classes.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.ScheduleDetailClassMapper;
import br.com.api.dto.ScheduleDetailClassDTO;
import br.com.api.entity.repository.ScheduleDetailClassRepository;
import lombok.NonNull;

@Component
public class InsertScheduleDetailClassFlowItem {

	@Autowired
	private ScheduleDetailClassRepository scheduleDetailClassRepository;

//	@Autowired
//	private UpdateScheduleDetailClassFlowItem updateScheduleDetailClassFlowItem;

	@Autowired
	private ScheduleDetailClassMapper classMapper;

	public ScheduleDetailClassDTO insert(@NonNull ScheduleDetailClassDTO clazz) {

		if (Objects.nonNull(clazz.getIdentifier())) {
//			return updateScheduleDetailClassFlowItem.update(clazz);
		}

		return classMapper.toDTO(scheduleDetailClassRepository.save(classMapper.toEntity(clazz)));
	}
}
