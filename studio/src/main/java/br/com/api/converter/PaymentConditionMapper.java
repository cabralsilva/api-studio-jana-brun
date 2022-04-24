package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.PaymentConditionDTO;
import br.com.api.entity.PaymentCondition;

@Mapper(componentModel = "spring")
public interface PaymentConditionMapper {

	public PaymentCondition toEntity(PaymentConditionDTO dto);

	public PaymentConditionDTO toDTO(PaymentCondition entity);
}
