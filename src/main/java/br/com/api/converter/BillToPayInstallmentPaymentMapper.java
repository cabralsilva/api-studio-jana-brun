package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.BillToPayInstallmentPaymentDTO;
import br.com.api.entity.BillToPayInstallmentPayment;

@Mapper(componentModel = "spring")
public interface BillToPayInstallmentPaymentMapper {

	public BillToPayInstallmentPayment toEntity(BillToPayInstallmentPaymentDTO dto);

	public BillToPayInstallmentPaymentDTO toDTO(BillToPayInstallmentPayment entity);
}
