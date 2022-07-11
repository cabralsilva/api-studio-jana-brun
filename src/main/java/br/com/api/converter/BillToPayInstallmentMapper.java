package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.BillToPayInstallmentDTO;
import br.com.api.entity.BillToPayInstallment;

@Mapper(componentModel = "spring")
public interface BillToPayInstallmentMapper {

	public BillToPayInstallment toEntity(BillToPayInstallmentDTO dto);

	public BillToPayInstallmentDTO toDTO(BillToPayInstallment entity);

}
