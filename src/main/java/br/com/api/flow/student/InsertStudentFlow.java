package br.com.api.flow.student;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.PersonDTO;
import br.com.api.dto.StudentDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.person.InsertPersonFlow;
import br.com.api.flow.student.item.InsertStudentFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertStudentFlow {

	@Autowired
	private InsertStudentFlowItem insertStudentFlowItem;
	@Autowired
	private InsertPersonFlow insertPersonFlow;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI<StudentDTO> execute(StudentDTO studentDTO, HttpHeaders headers) {

		ResponseAPI<StudentDTO> response = ResponseAPI.<StudentDTO>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			studentDTO.setPerson(PersonDTO.builder()
					.identifier(insertPersonFlow.execute(studentDTO.getPerson(), headers).getData().getIdentifier())
					.build());

			studentDTO.setResponsible(PersonDTO.builder()
					.identifier(insertPersonFlow.execute(studentDTO.getResponsible(), headers).getData().getIdentifier())
					.build());
			response.setData(insertStudentFlowItem.insert(studentDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
