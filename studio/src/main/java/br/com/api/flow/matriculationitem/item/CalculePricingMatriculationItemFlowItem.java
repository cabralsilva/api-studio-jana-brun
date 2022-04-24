package br.com.api.flow.matriculationitem.item;

import org.springframework.stereotype.Service;

import br.com.api.dto.MatriculationItemDTO;
import lombok.var;

@Service
public class CalculePricingMatriculationItemFlowItem {

	public MatriculationItemDTO calcule(MatriculationItemDTO MatriculationItemDTO, Double unitPrice) {

		MatriculationItemDTO.setUnitPrice(unitPrice);
		MatriculationItemDTO.setTotalPrice(MatriculationItemDTO.getUnitPrice() * MatriculationItemDTO.getQuantity());
		final var addition = MatriculationItemDTO.getTotalPrice() * (MatriculationItemDTO.getAdditionPercent() / 100);
		MatriculationItemDTO.setFinalPrice(MatriculationItemDTO.getTotalPrice() + addition);

		return MatriculationItemDTO;
	}
}
