package br.com.api.flow.notice.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_REGISTER_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.NoticeMapper;
import br.com.api.entity.Notice;
import br.com.api.entity.repository.NoticeFilter;
import br.com.api.entity.repository.NoticeRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindNoticeByFilterFlowItem {

	@Autowired
	private NoticeRepositoryImpl noticeRepositoryImpl;

	@Autowired
	private NoticeMapper noticeMapper;

	public NoticeFilter findByFilter(NoticeFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<Notice> entities = noticeRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> noticeMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<Notice> entities = noticeRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> noticeMapper.toDTO(entity)).toList());			
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_REGISTER_FOUND);
		}

		return filter;
	}
}
