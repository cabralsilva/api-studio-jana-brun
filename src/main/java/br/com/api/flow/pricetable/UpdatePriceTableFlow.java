package br.com.api.flow.pricetable;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.PriceTableDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.pricetable.item.UpdatePriceTableFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdatePriceTableFlow {

	@Autowired
	private UpdatePriceTableFlowItem updatePriceTableFlowItem;

	@Transactional
	public ResponseEntity<ResponseAPI<PriceTableDTO>> execute(PriceTableDTO matriculationDTO,
			HttpHeaders headers) {

		ResponseAPI<PriceTableDTO> response = ResponseAPI.<PriceTableDTO>builder()
				.friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(updatePriceTableFlowItem.update(matriculationDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
		}

		return ResponseEntity.ok(response);
	}
}
