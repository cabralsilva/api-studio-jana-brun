package br.com.api.flow.city;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.CityDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.city.item.UpdateCityFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdateCityFlow {

	@Autowired
	private UpdateCityFlowItem updateCityFlowItem;

	public ResponseAPI<CityDTO> execute(CityDTO cityDTO, HttpHeaders headers) {

		ResponseAPI<CityDTO> response = ResponseAPI.<CityDTO>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(updateCityFlowItem.update(cityDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
