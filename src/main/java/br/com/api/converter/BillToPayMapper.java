package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.BillToPayDTO;
import br.com.api.entity.BillToPay;

@Mapper(componentModel = "spring")
public interface BillToPayMapper {

	public BillToPay toEntity(BillToPayDTO dto);

	public BillToPayDTO toDTO(BillToPay entity);
}
