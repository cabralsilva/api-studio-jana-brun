package br.com.api.flow.pricetable.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.PriceTableMapper;
import br.com.api.dto.PriceTableDTO;
import br.com.api.entity.repository.PriceTableRepository;

@Component
public class UpdatePriceTableFlowItem {

	@Autowired
	private PriceTableRepository priceTableRepository;

	@Autowired
	private PriceTableMapper priceTableMapper;

	public PriceTableDTO update(PriceTableDTO priceTable) {

		return priceTableMapper.toDTO(priceTableRepository.save(priceTableMapper.toEntity(priceTable)));
	}
}
