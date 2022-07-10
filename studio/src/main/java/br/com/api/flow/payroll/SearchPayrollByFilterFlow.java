package br.com.api.flow.payroll;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.dto.PayrollDetailDTO;
import br.com.api.entity.repository.PayrollFilter;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.payroll.item.SearchPayrollByFilterFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class SearchPayrollByFilterFlow {

	@Autowired
	private SearchPayrollByFilterFlowItem searchPayrollByFilterFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseEntity<ResponseAPI<PayrollFilter>> execute(PayrollFilter filter, HttpHeaders headers) {

		ResponseAPI<PayrollFilter> response = ResponseAPI.<PayrollFilter>builder()
				.friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(searchPayrollByFilterFlowItem.search(filter));
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
