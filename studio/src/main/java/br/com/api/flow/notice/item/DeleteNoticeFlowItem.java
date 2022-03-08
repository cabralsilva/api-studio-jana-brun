package br.com.api.flow.notice.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.NoticeRepository;

@Component
public class DeleteNoticeFlowItem {

	@Autowired
	private NoticeRepository noticeRepository;

	public void delete(Integer identifier) {

		noticeRepository.deleteById(identifier);
	}
}
