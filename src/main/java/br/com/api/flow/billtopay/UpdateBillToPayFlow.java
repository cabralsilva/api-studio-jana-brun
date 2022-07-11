package br.com.api.flow.billtopay;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.dto.BillToPayDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.billtopay.item.UpdateBillToPayFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdateBillToPayFlow {

	@Autowired
	private UpdateBillToPayFlowItem updateBillToPayFlowItem;

	public ResponseEntity<ResponseAPI<BillToPayDTO>> execute(BillToPayDTO billToPayDTO, HttpHeaders headers) {

		ResponseAPI<BillToPayDTO> response = ResponseAPI.<BillToPayDTO>builder().friendlyMessagesList(new ArrayList<>())
				.build();

		try {
			response.setData(updateBillToPayFlowItem.update(billToPayDTO));
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
