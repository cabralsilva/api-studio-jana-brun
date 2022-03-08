package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.NoticeDTO;
import br.com.api.entity.Notice;

@Mapper(componentModel = "spring")
public interface NoticeMapper {

	public Notice toEntity(NoticeDTO dto);

	public NoticeDTO toDTO(Notice entity);
}
