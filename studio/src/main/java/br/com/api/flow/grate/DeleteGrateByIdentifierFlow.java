package br.com.api.flow.grate;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.country.item.DeleteCountryFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class DeleteGrateByIdentifierFlow {

	@Autowired
	private DeleteCountryFlowItem deleteCountryFlowItem;

	public ResponseAPI execute(Integer identifier , HttpHeaders headers) {

		ResponseAPI response = ResponseAPI.builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			deleteCountryFlowItem.delete(identifier);
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);
			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
