package br.com.api.flow.payroll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.dto.PayrollDTO;
import br.com.api.entity.repository.PayrollFilter;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.payroll.item.CheckoutPayrollFlowItem;
import br.com.api.flow.payroll.item.SearchPayrollByFilterFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class CheckoutPayrollFlow {

	@Autowired
	private SearchPayrollByFilterFlowItem searchPayrollByFilterFlowItem;
	
	@Autowired
	private CheckoutPayrollFlowItem checkoutPayrollFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseEntity<ResponseAPI<Void>> execute(Integer identifier, HttpHeaders headers) {

		ResponseAPI<Void> response = ResponseAPI.<Void>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			List<PayrollDTO> payrollList = searchPayrollByFilterFlowItem.search(
					PayrollFilter.builder().example(PayrollDTO.builder().identifier(identifier).build()).build())
					.getResult();
			if (payrollList.isEmpty()) {
				throw new Exception("Folha de pagamento não encontrada");
			}
			
			checkoutPayrollFlowItem.checkout(payrollList.get(0));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);
			response.setFriendlyMessagesList(Arrays.asList(e.getMessage()));
			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
		}

		return ResponseEntity.ok(response);
	}
}
