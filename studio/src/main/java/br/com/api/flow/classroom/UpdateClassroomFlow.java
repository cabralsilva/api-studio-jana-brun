package br.com.api.flow.classroom;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.ClassroomDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.classroom.item.UpdateClassroomFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdateClassroomFlow {

	@Autowired
	private UpdateClassroomFlowItem updateClassroomFlowItem;

	public ResponseAPI<ClassroomDTO> execute(ClassroomDTO classroomDTO, HttpHeaders headers) {

		ResponseAPI<ClassroomDTO> response = ResponseAPI.<ClassroomDTO>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(updateClassroomFlowItem.update(classroomDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
