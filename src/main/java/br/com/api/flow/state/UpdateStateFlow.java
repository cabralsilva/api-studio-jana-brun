package br.com.api.flow.state;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.StateDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.state.item.UpdateStateFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdateStateFlow {

	@Autowired
	private UpdateStateFlowItem updateStateFlowItem;

	public ResponseAPI<StateDTO> execute(StateDTO stateDTO, HttpHeaders headers) {

		ResponseAPI<StateDTO> response = ResponseAPI.<StateDTO>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(updateStateFlowItem.update(stateDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
