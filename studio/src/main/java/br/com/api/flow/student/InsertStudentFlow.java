package br.com.api.flow.student;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.StudentDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.student.item.InsertStudentFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertStudentFlow {

	@Autowired
	private InsertStudentFlowItem insertStudentFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI execute(StudentDTO studentDTO, HttpHeaders headers) {

		ResponseAPI response = ResponseAPI.builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
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
