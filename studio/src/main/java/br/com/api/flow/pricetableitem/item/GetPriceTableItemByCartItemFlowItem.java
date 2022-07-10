package br.com.api.flow.pricetableitem.item;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.api.dto.GrateItemDTO;
import br.com.api.dto.MatriculationItemDTO;
import br.com.api.dto.PriceTableItemDTO;
import br.com.api.flow.pricetable.item.GetPriceTableEffectiveFlowItem;
import lombok.NonNull;

@Service
public class GetPriceTableItemByCartItemFlowItem {

	@Autowired
	private GetPriceTableEffectiveFlowItem getPriceTableEffectiveFlowItem;

	@Autowired
	private MessageSource messageSource;

	public Optional<PriceTableItemDTO> get(@NonNull MatriculationItemDTO matriculationItemDTO,
			@NonNull List<PriceTableItemDTO> priceTableItemDTOList) {

		priceTableItemDTOList = priceTableItemDTOList.stream().filter(
				item -> item.getProduct().getIdentifier().equals(matriculationItemDTO.getProduct().getIdentifier()))
				.collect(Collectors.toList());

		Optional<PriceTableItemDTO> priceTableItemDTO = Optional.empty();
		long score = 0;
		for (PriceTableItemDTO priceTableItemDTOAux : priceTableItemDTOList) {
			long minScore = priceTableItemDTOAux.getGrateItemList().stream()
					.filter(distinctByKey(item -> item.getGrate().getIdentifier())).count();
			
			
			List<GrateItemDTO> grateItemIntersection = priceTableItemDTOAux.getGrateItemList().stream()
					.filter(matriculationItemDTO.getGrateItemList()::contains).collect(Collectors.toList());

			if (grateItemIntersection.size() >= minScore && grateItemIntersection.size() >= score) {
				priceTableItemDTO = Optional.of(priceTableItemDTOAux);
				score = grateItemIntersection.size();
				continue;
			}

			if (ObjectUtils.isEmpty(grateItemIntersection) && minScore == 0 && score == 0) {
				priceTableItemDTO = Optional.of(priceTableItemDTOAux);
				score = minScore;
			}
		}

		return priceTableItemDTO;
	}

	private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}
}
