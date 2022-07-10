package br.com.api.flow.employee;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.EmployeeDTO;
import br.com.api.dto.PersonDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.address.CheckAddressFlow;
import br.com.api.flow.employee.item.InsertEmployeeFlowItem;
import br.com.api.flow.person.CheckPersonFlow;
import br.com.api.flow.person.InsertPersonFlow;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertEmployeeFlow {

	@Autowired
	private InsertEmployeeFlowItem insertEmployeeFlowItem;

	@Autowired
	private CheckAddressFlow checkAddressFlow;
	@Autowired
	private CheckPersonFlow checkPersonFlow;
	@Autowired
	private InsertPersonFlow insertPersonFlow;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private PasswordEncoder encoder;

	@Transactional
	public ResponseEntity<ResponseAPI<EmployeeDTO>> execute(EmployeeDTO employeeDTO, HttpHeaders headers) throws Exception {

		ResponseAPI<EmployeeDTO> response = ResponseAPI.<EmployeeDTO>builder().friendlyMessagesList(new ArrayList<>())
				.build();

		try {
			employeeDTO.setPerson(PersonDTO.builder()
					.identifier(insertPersonFlow.execute(employeeDTO.getPerson(), headers).getData().getIdentifier())
					.build());

			employeeDTO.setPassword(encoder.encode("123456"));
			response.setData(insertEmployeeFlowItem.insert(employeeDTO));
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
