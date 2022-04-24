package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.BillToReceiveDTO;
import br.com.api.entity.BillToReceive;

@Mapper(componentModel = "spring")
public interface BillToReceiveMapper {

	public BillToReceive toEntity(BillToReceiveDTO dto);

	public BillToReceiveDTO toDTO(BillToReceive entity);
}
