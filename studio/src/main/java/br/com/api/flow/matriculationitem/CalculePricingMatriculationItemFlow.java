package br.com.api.flow.matriculationitem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.MatriculationItemDTO;
import br.com.api.entity.repository.ProductFilter;
import br.com.api.flow.matriculationitem.item.CalculePricingMatriculationItemFlowItem;
import br.com.api.flow.product.item.FindProductByFilterFlowItem;
import lombok.var;

@Service
public class CalculePricingMatriculationItemFlow {

	@Autowired
	private FindProductByFilterFlowItem findProductByFilterFlowItem;
	@Autowired
	private CalculePricingMatriculationItemFlowItem calculePricingMatriculationItemFlowItem;

	public MatriculationItemDTO execute(MatriculationItemDTO MatriculationItemDTO) throws Exception {

		final var product = findProductByFilterFlowItem.findByFilter(
				ProductFilter.builder().example(MatriculationItemDTO.getProduct()).resultUnique(Boolean.TRUE).build())
				.getResult().stream().findFirst();

		if (product.isPresent())
			return calculePricingMatriculationItemFlowItem.calcule(MatriculationItemDTO, product.get().getUnitPrice());

		throw new Exception("unitPrice not found - try not implemented yet");
	}
}
