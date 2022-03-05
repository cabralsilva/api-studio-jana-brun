package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.AddressDTO;
import br.com.api.entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

	public Address toEntity(AddressDTO dto);

	public AddressDTO toDTO(Address entity);
}
