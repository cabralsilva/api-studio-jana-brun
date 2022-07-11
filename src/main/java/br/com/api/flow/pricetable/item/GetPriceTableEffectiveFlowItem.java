package br.com.api.flow.pricetable.item;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.PriceTableMapper;
import br.com.api.dto.PriceTableDTO;
import br.com.api.entity.PriceTable;
import br.com.api.entity.repository.PriceTableRepository;

@Component
public class GetPriceTableEffectiveFlowItem {

	@Autowired
	private PriceTableRepository priceTableRepository;

	@Autowired
	private PriceTableMapper priceTableMapper;

	public Optional<PriceTableDTO> get() {

		PriceTable priceTable = priceTableRepository
				.findFirstByInitDateTimeLessThanEqualAndEndDateTimeGreaterThanEqualOrderByCreationDateTimeDesc(
						LocalDateTime.now(), LocalDateTime.now());

		if (ObjectUtils.isNotEmpty(priceTable)) {
			return Optional.of(priceTableMapper.toDTO(priceTable));
		}
		return Optional.empty();
	}
}
