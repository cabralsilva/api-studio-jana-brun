package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.BillToReceiveInstallmentPaymentDTO;
import br.com.api.entity.BillToReceiveInstallmentPayment;

@Mapper(componentModel = "spring")
public interface BillToReceiveInstallmentPaymentMapper {

	public BillToReceiveInstallmentPayment toEntity(BillToReceiveInstallmentPaymentDTO dto);

	public BillToReceiveInstallmentPaymentDTO toDTO(BillToReceiveInstallmentPayment entity);
}
