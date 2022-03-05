package br.com.api.flow.product.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.ProductMapper;
import br.com.api.dto.ProductDTO;
import br.com.api.entity.repository.ProductRepository;

@Component
public class InsertProductFlowItem {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UpdateProductFlowItem updateProductFlowItem;

	@Autowired
	private ProductMapper productMapper;

	public ProductDTO insert(@NonNull ProductDTO product) {

		if (Objects.nonNull(product.getIdentifier())) {
			return updateProductFlowItem.update(product);
		}

		return productMapper.toDTO(productRepository.save(productMapper.toEntity(product)));
	}
}
