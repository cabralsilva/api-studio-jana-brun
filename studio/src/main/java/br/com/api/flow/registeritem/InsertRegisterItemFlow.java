package br.com.api.flow.registeritem;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.RegisterItemDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.registeritem.item.InsertRegisterItemFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertRegisterItemFlow {

	@Autowired
	private InsertRegisterItemFlowItem insertRegisterItemFlowItem;
	@Autowired
	private CalculePricingRegisterItemFlow calculePricingRegisterItemFlow;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI execute(RegisterItemDTO registerItemDTO, HttpHeaders headers) {

		ResponseAPI response = ResponseAPI.builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			calculePricingRegisterItemFlow.execute(registerItemDTO);
			response.setData(insertRegisterItemFlowItem.insert(registerItemDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
