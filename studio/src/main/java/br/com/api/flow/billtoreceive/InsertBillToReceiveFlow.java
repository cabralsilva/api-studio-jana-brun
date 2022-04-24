package br.com.api.flow.billtoreceive;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.BillToReceiveDTO;
import br.com.api.enums.StatusOfBillEnum;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.billtoreceive.item.InsertBillToReceiveFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertBillToReceiveFlow {

	@Autowired
	private InsertBillToReceiveFlowItem insertBillToReceiveFlowItem;

	@Autowired
	private MessageSource messageSource;

	@Transactional
	public ResponseAPI execute(BillToReceiveDTO billToReceiveDTO, HttpHeaders headers) {

		ResponseAPI response = ResponseAPI.builder().friendlyMessagesList(new ArrayList<>()).build();

		try {

			billToReceiveDTO.setStatus(StatusOfBillEnum.OPENED);
			response.setData(insertBillToReceiveFlowItem.insert(billToReceiveDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
