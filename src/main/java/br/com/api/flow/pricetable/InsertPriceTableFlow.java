package br.com.api.flow.pricetable;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.dto.PriceTableDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.pricetable.item.InsertPriceTableFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertPriceTableFlow {

	@Autowired
	private InsertPriceTableFlowItem insertPriceTableFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseEntity<ResponseAPI<PriceTableDTO>> execute(PriceTableDTO productDTO, HttpHeaders headers) {

		ResponseAPI<PriceTableDTO> response = ResponseAPI.<PriceTableDTO>builder().friendlyMessagesList(new ArrayList<>())
				.build();

		try {
			response.setData(insertPriceTableFlowItem.insert(productDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());

			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
		}

		return ResponseEntity.ok(response);
	}
}