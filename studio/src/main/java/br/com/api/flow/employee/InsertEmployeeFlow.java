package br.com.api.flow.employee;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.EmployeeDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.address.CheckAddressFlow;
import br.com.api.flow.employee.item.InsertEmployeeFlowItem;
import br.com.api.flow.individual.CheckIndividualFlow;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertEmployeeFlow {

	@Autowired
	private InsertEmployeeFlowItem insertEmployeeFlowItem;
	
	@Autowired
	private CheckAddressFlow checkAddressFlow;
	@Autowired
	private CheckIndividualFlow checkIndividualFlow;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI execute(EmployeeDTO employeeDTO, HttpHeaders headers) {

		ResponseAPI response = ResponseAPI.builder().friendlyMessagesList(new ArrayList<>()).build();
		
		try {
			checkIndividualFlow.execute(employeeDTO.getIndividual());
			checkAddressFlow.execute(employeeDTO.getIndividual().getAddress());
			
			response.setData(insertEmployeeFlowItem.insert(employeeDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
