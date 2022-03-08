package br.com.api.flow.registeritem;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.RegisterItemDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.registeritem.item.UpdateRegisterItemFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdateRegisterItemFlow {

	@Autowired
	private UpdateRegisterItemFlowItem updateRegisterItemFlowItem;
	@Autowired
	private CalculePricingRegisterItemFlow calculePricingRegisterItemFlow;

	public ResponseAPI execute(RegisterItemDTO registerItemDTO, HttpHeaders headers) {

		ResponseAPI response = ResponseAPI.builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			calculePricingRegisterItemFlow.execute(registerItemDTO);
			response.setData(updateRegisterItemFlowItem.update(registerItemDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
