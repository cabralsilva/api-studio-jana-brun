package br.com.api.flow.product.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.ProductRepository;

@Component
public class DeleteProductFlowItem {

	@Autowired
	private ProductRepository productRepository;

	public void delete(Integer identifier) {

		productRepository.deleteById(identifier);
	}
}
