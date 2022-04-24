package br.com.api.flow.notice;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.NoticeDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.notice.item.UpdateNoticeFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdateNoticeFlow {

	@Autowired
	private UpdateNoticeFlowItem updateNoticeFlowItem;

	public ResponseAPI<NoticeDTO> execute(NoticeDTO noticeDTO, HttpHeaders headers) {

		ResponseAPI<NoticeDTO> response = ResponseAPI.<NoticeDTO>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(updateNoticeFlowItem.update(noticeDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
