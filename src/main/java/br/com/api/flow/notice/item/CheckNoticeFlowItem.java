package br.com.api.flow.notice.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.NoticeDTO;
import br.com.api.entity.repository.NoticeFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckNoticeFlowItem {

	@Autowired
	private FindNoticeByFilterFlowItem findNoticeByFilterFlowItem;

	public NoticeDTO checkIfExist(NoticeDTO countryDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new NoticeFilter();
		filter.setExample(countryDTO);
		filter.setPageable(Boolean.FALSE);

		final var existing = findNoticeByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			countryDTO.setIdentifier(existing.get().getIdentifier());
		}
		return countryDTO;
	}
}
