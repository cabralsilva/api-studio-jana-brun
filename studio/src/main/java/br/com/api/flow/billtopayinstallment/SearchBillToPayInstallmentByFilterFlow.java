package br.com.api.flow.billtopayinstallment;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.entity.repository.BillToPayInstallmentFilter;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.billtopayinstallment.item.SearchBillToPayInstallmentByFilterFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class SearchBillToPayInstallmentByFilterFlow {

	@Autowired
	private SearchBillToPayInstallmentByFilterFlowItem findBillToPayInstallmentByFilterFlowItem;

	public ResponseEntity<ResponseAPI<BillToPayInstallmentFilter>> execute(BillToPayInstallmentFilter filter,
			HttpHeaders headers) {

		ResponseAPI<BillToPayInstallmentFilter> response = ResponseAPI.<BillToPayInstallmentFilter>builder()
				.friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(findBillToPayInstallmentByFilterFlowItem.search(filter));
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
