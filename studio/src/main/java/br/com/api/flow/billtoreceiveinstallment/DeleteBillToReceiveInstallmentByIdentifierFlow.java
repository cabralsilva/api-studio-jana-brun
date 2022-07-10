package br.com.api.flow.billtoreceiveinstallment;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.billtoreceiveinstallment.item.DeleteBillToReceiveInstallmentFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class DeleteBillToReceiveInstallmentByIdentifierFlow {

	@Autowired
	private DeleteBillToReceiveInstallmentFlowItem deleteBillToReceiveInstallmentFlowItem;

	public ResponseEntity<ResponseAPI<Void>> execute(Integer identifier , HttpHeaders headers) {

		ResponseAPI<Void> response = ResponseAPI.<Void>builder()
				.friendlyMessagesList(new ArrayList<>()).build();
		try {
			deleteBillToReceiveInstallmentFlowItem.delete(identifier);
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
