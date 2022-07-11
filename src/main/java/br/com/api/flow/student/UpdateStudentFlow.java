package br.com.api.flow.student;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.PersonDTO;
import br.com.api.dto.StudentDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.person.UpdatePersonFlow;
import br.com.api.flow.student.item.UpdateStudentFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdateStudentFlow {

	@Autowired
	private UpdateStudentFlowItem updateStudentFlowItem;
	@Autowired
	private UpdatePersonFlow updatePersonFlow;

	public ResponseAPI<StudentDTO> execute(StudentDTO studentDTO, HttpHeaders headers) {

		ResponseAPI<StudentDTO> response = ResponseAPI.<StudentDTO>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			studentDTO.setPerson(PersonDTO.builder()
					.identifier(updatePersonFlow.execute(studentDTO.getPerson(), headers).getData().getIdentifier())
					.build());
			studentDTO.setResponsible(PersonDTO.builder()
					.identifier(updatePersonFlow.execute(studentDTO.getResponsible(), headers).getData().getIdentifier())
					.build());
			response.setData(updateStudentFlowItem.update(studentDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
