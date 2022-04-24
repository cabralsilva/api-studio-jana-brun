package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.SupplierDTO;
import br.com.api.entity.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

	public Supplier toEntity(SupplierDTO dto);

	public SupplierDTO toDTO(Supplier entity);
}
