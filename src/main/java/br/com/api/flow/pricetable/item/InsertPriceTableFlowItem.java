package br.com.api.flow.pricetable.item;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.PriceTableMapper;
import br.com.api.dto.PriceTableDTO;
import br.com.api.entity.repository.PriceTableRepository;

@Component
public class InsertPriceTableFlowItem {

	@Autowired
	private PriceTableRepository priceTableRepository;

	@Autowired
	private UpdatePriceTableFlowItem updatePriceTableFlowItem;

	@Autowired
	private PriceTableMapper priceTableMapper;

	public PriceTableDTO insert(@NonNull PriceTableDTO priceTableDTO) {

		if (ObjectUtils.isNotEmpty(priceTableDTO.getIdentifier())) {
			return updatePriceTableFlowItem.update(priceTableDTO);
		}

		return priceTableMapper.toDTO(priceTableRepository.save(priceTableMapper.toEntity(priceTableDTO)));
	}
}
