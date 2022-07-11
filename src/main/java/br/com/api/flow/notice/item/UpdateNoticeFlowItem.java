package br.com.api.flow.notice.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.NoticeMapper;
import br.com.api.dto.NoticeDTO;
import br.com.api.entity.repository.NoticeRepository;

@Component
public class UpdateNoticeFlowItem {

	@Autowired
	private NoticeRepository noticeRepository;

	@Autowired
	private NoticeMapper noticeMapper;

	public NoticeDTO update(NoticeDTO notice) {

		return noticeMapper.toDTO(noticeRepository.save(noticeMapper.toEntity(notice)));
	}
}
