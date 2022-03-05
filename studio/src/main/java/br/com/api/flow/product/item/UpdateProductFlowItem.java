package br.com.api.flow.product.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.ProductMapper;
import br.com.api.dto.ProductDTO;
import br.com.api.entity.repository.ProductRepository;

@Component
public class UpdateProductFlowItem {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductMapper productMapper;

	public ProductDTO update(ProductDTO product) {

		return productMapper.toDTO(productRepository.save(productMapper.toEntity(product)));
	}
}
