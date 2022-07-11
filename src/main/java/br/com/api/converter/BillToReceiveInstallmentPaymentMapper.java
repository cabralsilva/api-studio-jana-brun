package br.com.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.api.dto.BillToReceiveDTO;
import br.com.api.dto.BillToReceiveDTO.BillToReceiveDTOBuilder;
import br.com.api.dto.BillToReceiveInstallmentDTO;
import br.com.api.dto.BillToReceiveInstallmentPaymentDTO;
import br.com.api.entity.BillToReceive;
import br.com.api.entity.BillToReceiveInstallment;
import br.com.api.entity.BillToReceiveInstallmentPayment;

@Mapper(componentModel = "spring")
public interface BillToReceiveInstallmentPaymentMapper {

	public BillToReceiveInstallmentPayment toEntity(BillToReceiveInstallmentPaymentDTO dto);

	@Mapping(source = "installment", target = "installment", qualifiedByName = "toInstallmentDTO")
	public BillToReceiveInstallmentPaymentDTO toDTO(BillToReceiveInstallmentPayment entity);

	@Named("toInstallmentDTO")
	public default BillToReceiveInstallmentDTO toInstallmentDTO(BillToReceiveInstallment source) {
		if (source == null) {
			return null;
		}

		BillToReceiveInstallmentDTO target = BillToReceiveInstallmentDTO.builder().build();

		target.setIdentifier(source.getIdentifier());
		target.setDescription(source.getDescription());
		target.setNumber(source.getNumber());
		target.setStatus(source.getStatus());
		target.setCreationDateTime(source.getCreationDateTime());
		target.setTargetDate(source.getTargetDate());
		target.setOriginalValue(source.getOriginalValue());
		target.setAdditionValue(source.getAdditionValue());
		target.setValue(source.getValue());

		return target;
	}

	public default BillToReceiveDTO billToReceiveToBillToReceiveDTO(BillToReceive billToReceive) {
		if (billToReceive == null) {
			return null;
		}

		BillToReceiveDTOBuilder<?, ?> billToReceiveDTO = BillToReceiveDTO.builder();

		billToReceiveDTO.identifier(billToReceive.getIdentifier());
		billToReceiveDTO.title(billToReceive.getTitle());
		billToReceiveDTO.status(billToReceive.getStatus());
		billToReceiveDTO.creationDateTime(billToReceive.getCreationDateTime());
		billToReceiveDTO.emissionDate(billToReceive.getEmissionDate());
		billToReceiveDTO.value(billToReceive.getValue());
		return billToReceiveDTO.build();
	}
}
