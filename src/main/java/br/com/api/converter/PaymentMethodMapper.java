package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.PaymentMethodDTO;
import br.com.api.entity.PaymentMethod;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

	public PaymentMethod toEntity(PaymentMethodDTO dto);

	public PaymentMethodDTO toDTO(PaymentMethod entity);
}
