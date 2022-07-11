package br.com.api.flow.notice.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.NoticeMapper;
import br.com.api.dto.NoticeDTO;
import br.com.api.entity.repository.NoticeRepository;

@Component
public class InsertNoticeFlowItem {

	@Autowired
	private NoticeRepository noticeRepository;

	@Autowired
	private UpdateNoticeFlowItem updateNoticeFlowItem;

	@Autowired
	private NoticeMapper noticeMapper;

	public NoticeDTO insert(@NonNull NoticeDTO notice) {

		if (Objects.nonNull(notice.getIdentifier())) {
			return updateNoticeFlowItem.update(notice);
		}

		return noticeMapper.toDTO(noticeRepository.save(noticeMapper.toEntity(notice)));
	}
}
