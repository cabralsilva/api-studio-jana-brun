package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.BillToReceiveInstallmentDTO;
import br.com.api.entity.BillToReceiveInstallment;

@Mapper(componentModel = "spring")
public interface BillToReceiveInstallmentMapper {

	public BillToReceiveInstallment toEntity(BillToReceiveInstallmentDTO dto);

	public BillToReceiveInstallmentDTO toDTO(BillToReceiveInstallment entity);
}
