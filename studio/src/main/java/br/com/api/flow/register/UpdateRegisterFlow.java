package br.com.api.flow.register;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.RegisterDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.register.item.UpdateRegisterFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdateRegisterFlow {

	@Autowired
	private UpdateRegisterFlowItem updateRegisterFlowItem;

	public ResponseAPI execute(RegisterDTO registerDTO, HttpHeaders headers) {

		ResponseAPI response = ResponseAPI.builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(updateRegisterFlowItem.update(registerDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
