package br.com.api.flow.matriculation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.dto.MatriculationDTO;
import br.com.api.entity.repository.MatriculationFilter;
import br.com.api.enums.LevelReport;
import br.com.api.enums.MatriculationStatusEnum;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.matriculation.item.FindMatriculationByFilterFlowItem;
import br.com.api.flow.matriculation.item.UpdateStatusMatriculationFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;
import lombok.var;

@Component
public class EffectiveMatriculationFlow {

	@Autowired
	private FindMatriculationByFilterFlowItem findMatriculationByFilterFlowItem;
	@Autowired
	private UpdateStatusMatriculationFlowItem updateStatusMatriculationFlowItem;

	public ResponseAPI<Void> execute(@NonNull Integer MatriculationIdentifier) {

		ResponseAPI<Void> response = ResponseAPI.<Void>builder().friendlyMessagesList(new ArrayList<>()).build();
		try {
			var existing = findMatriculationByFilterFlowItem.findByFilter(MatriculationFilter.builder()
					.example(MatriculationDTO.builder().identifier(MatriculationIdentifier).build())
					.resultUnique(Boolean.TRUE).build()).getResult().stream().findFirst();
			if (existing.isPresent()) {
				existing.get().setStatus(MatriculationStatusEnum.EFFECTIVE);
				updateStatusMatriculationFlowItem.update(existing.get());
			}
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
