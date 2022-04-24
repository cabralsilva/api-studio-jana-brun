package br.com.api.flow.billtoreceiveinstallment;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.BillToReceiveInstallmentDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.billtoreceiveinstallment.item.InsertBillToReceiveInstallmentFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertBillToReceiveInstallmentFlow {

	@Autowired
	private InsertBillToReceiveInstallmentFlowItem insertBillToReceiveInstallmentFlowItem;

	@Autowired
	private MessageSource messageSource;

	@Transactional
	public ResponseAPI execute(BillToReceiveInstallmentDTO billToReceiveInstallmentDTO, HttpHeaders headers) {

		ResponseAPI response = ResponseAPI.builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(insertBillToReceiveInstallmentFlowItem.insert(billToReceiveInstallmentDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
