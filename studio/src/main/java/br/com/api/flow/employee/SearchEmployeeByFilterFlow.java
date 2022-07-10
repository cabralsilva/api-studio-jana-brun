package br.com.api.flow.employee;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.entity.repository.EmployeeFilter;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.employee.item.SearchEmployeeByFilterFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class SearchEmployeeByFilterFlow {

	@Autowired
	private SearchEmployeeByFilterFlowItem findEmployeeByFilterFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseEntity<ResponseAPI<EmployeeFilter>> execute(EmployeeFilter filter, HttpHeaders headers) {

		ResponseAPI<EmployeeFilter> response = ResponseAPI.<EmployeeFilter>builder()
				.friendlyMessagesList(new ArrayList<>()).build();
		try {
			response.setData(findEmployeeByFilterFlowItem.search(filter));
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
