package br.com.api.flow.employee;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.api.dto.EmployeeDTO;
import br.com.api.dto.PersonDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.employee.item.UpdateEmployeeFlowItem;
import br.com.api.flow.person.UpdatePersonFlow;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdateEmployeeFlow {

	@Autowired
	private UpdateEmployeeFlowItem updateEmployeeFlowItem;
	@Autowired
	private UpdatePersonFlow updatePersonFlow;

	public ResponseEntity<ResponseAPI<EmployeeDTO>> execute(EmployeeDTO employeeDTO, HttpHeaders headers) {

		ResponseAPI<EmployeeDTO> response = ResponseAPI.<EmployeeDTO>builder().friendlyMessagesList(new ArrayList<>())
				.build();

		try {
			employeeDTO.setPerson(PersonDTO.builder()
					.identifier(updatePersonFlow.execute(employeeDTO.getPerson(), headers).getData().getIdentifier())
					.build());
			response.setData(updateEmployeeFlowItem.update(employeeDTO));
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
