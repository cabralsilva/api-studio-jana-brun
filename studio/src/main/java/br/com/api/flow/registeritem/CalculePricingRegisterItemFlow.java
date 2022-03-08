package br.com.api.flow.registeritem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.RegisterItemDTO;
import br.com.api.entity.repository.ProductFilter;
import br.com.api.flow.product.item.FindProductByFilterFlowItem;
import br.com.api.flow.registeritem.item.CalculePricingRegisterItemFlowItem;

@Service
public class CalculePricingRegisterItemFlow {

	@Autowired
	private FindProductByFilterFlowItem findProductByFilterFlowItem;
	@Autowired
	private CalculePricingRegisterItemFlowItem calculePricingRegisterItemFlowItem;

	public RegisterItemDTO execute(RegisterItemDTO registerItemDTO) throws Exception {

		final var product = findProductByFilterFlowItem.findByFilter(
				ProductFilter.builder().example(registerItemDTO.getProduct()).resultUnique(Boolean.TRUE).build())
				.getResult().stream().findFirst();

		if (product.isPresent())
			return calculePricingRegisterItemFlowItem.calcule(registerItemDTO, product.get().getUnitPrice());

		throw new Exception("unitPrice not found - try not implemented yet");
	}
}
