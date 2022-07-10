package br.com.api.flow.billtoreceive;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.billtoreceive.item.DeleteBillToReceiveFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class DeleteBillToReceiveByIdentifierFlow {

	@Autowired
	private DeleteBillToReceiveFlowItem deleteBillToReceiveFlowItem;

	public ResponseEntity<ResponseAPI<Void>> execute(Integer identifier , HttpHeaders headers) {

		ResponseAPI<Void> response = ResponseAPI.<Void>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			deleteBillToReceiveFlowItem.delete(identifier);
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
