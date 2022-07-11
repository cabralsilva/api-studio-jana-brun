package br.com.api.flow.matriculationitem;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.MatriculationItemDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.matriculationitem.item.InsertMatriculationItemFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertMatriculationItemFlow {

	@Autowired
	private InsertMatriculationItemFlowItem insertMatriculationItemFlowItem;
	@Autowired
	private CalculePricingMatriculationItemFlow calculePricingMatriculationItemFlow;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI execute(MatriculationItemDTO MatriculationItemDTO, HttpHeaders headers) {

		ResponseAPI response = ResponseAPI.builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			calculePricingMatriculationItemFlow.execute(MatriculationItemDTO);
			response.setData(insertMatriculationItemFlowItem.insert(MatriculationItemDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
