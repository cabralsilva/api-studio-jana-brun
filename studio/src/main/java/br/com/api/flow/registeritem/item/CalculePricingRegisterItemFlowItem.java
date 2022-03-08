package br.com.api.flow.registeritem.item;

import org.springframework.stereotype.Service;

import br.com.api.dto.RegisterItemDTO;

@Service
public class CalculePricingRegisterItemFlowItem {

	public RegisterItemDTO calcule(RegisterItemDTO registerItemDTO, Double unitPrice) {

		registerItemDTO.setUnitPrice(unitPrice);
		registerItemDTO.setTotalPrice(registerItemDTO.getUnitPrice() * registerItemDTO.getQuantity());
		final var addition = registerItemDTO.getTotalPrice() * (registerItemDTO.getAdditionPercent() / 100);
		registerItemDTO.setFinalPrice(registerItemDTO.getTotalPrice() + addition);

		return registerItemDTO;
	}
}
