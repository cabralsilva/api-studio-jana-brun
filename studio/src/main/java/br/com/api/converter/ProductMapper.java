package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.ProductDTO;
import br.com.api.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	public Product toEntity(ProductDTO dto);

	public ProductDTO toDTO(Product entity);
}
