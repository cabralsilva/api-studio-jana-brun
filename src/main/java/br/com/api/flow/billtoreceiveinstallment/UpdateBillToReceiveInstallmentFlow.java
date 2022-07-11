package br.com.api.flow.billtoreceiveinstallment;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.dto.BillToReceiveInstallmentDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.billtoreceiveinstallment.item.UpdateBillToReceiveInstallmentFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdateBillToReceiveInstallmentFlow {

	@Autowired
	private UpdateBillToReceiveInstallmentFlowItem updateBillToReceiveInstallmentFlowItem;

	public ResponseEntity<ResponseAPI<BillToReceiveInstallmentDTO>> execute(BillToReceiveInstallmentDTO billToReceiveInstallmentDTO, HttpHeaders headers) {

		ResponseAPI<BillToReceiveInstallmentDTO> response = ResponseAPI.<BillToReceiveInstallmentDTO>builder()
				.friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(updateBillToReceiveInstallmentFlowItem.update(billToReceiveInstallmentDTO));
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
