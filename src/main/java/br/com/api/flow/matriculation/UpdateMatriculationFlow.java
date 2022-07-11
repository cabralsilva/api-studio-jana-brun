package br.com.api.flow.matriculation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.MatriculationDTO;
import br.com.api.dto.StudentDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.matriculation.item.UpdateMatriculationFlowItem;
import br.com.api.flow.student.UpdateStudentFlow;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdateMatriculationFlow {

	@Autowired
	private UpdateMatriculationFlowItem updateMatriculationFlowItem;
	@Autowired
	private UpdateStudentFlow updateStudentFlow;

	@Transactional
	public ResponseEntity<ResponseAPI<MatriculationDTO>> execute(MatriculationDTO matriculationDTO,
			HttpHeaders headers) {

		ResponseAPI<MatriculationDTO> response = ResponseAPI.<MatriculationDTO>builder()
				.friendlyMessagesList(new ArrayList<>()).build();

		try {
			matriculationDTO.setStudent(StudentDTO.builder()
					.identifier(
							updateStudentFlow.execute(matriculationDTO.getStudent(), headers).getData().getIdentifier())
					.build());
			response.setData(updateMatriculationFlowItem.update(matriculationDTO));
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
