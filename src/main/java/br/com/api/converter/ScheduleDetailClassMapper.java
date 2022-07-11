package br.com.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.api.dto.ScheduleDetailClassDTO;
import br.com.api.entity.ScheduleDetailClass;

@Mapper(componentModel = "spring")
public interface ScheduleDetailClassMapper {

	public ScheduleDetailClass toEntity(ScheduleDetailClassDTO dto);

	@Mapping(target =  "clazz", ignore = true)
	public ScheduleDetailClassDTO toDTO(ScheduleDetailClass entity);
}
