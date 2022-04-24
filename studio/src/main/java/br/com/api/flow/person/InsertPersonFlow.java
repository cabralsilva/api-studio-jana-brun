package br.com.api.flow.person;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.PersonDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.address.CheckAddressFlow;
import br.com.api.flow.person.item.InsertPersonFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertPersonFlow {

	@Autowired
	private InsertPersonFlowItem insertPersonFlowItem;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CheckAddressFlow checkAddressFlow;
	@Autowired
	private CheckPersonFlow checkPersonFlow;

	public ResponseAPI<PersonDTO> execute(PersonDTO personDTO, HttpHeaders headers) throws Exception {

		ResponseAPI<PersonDTO> response = ResponseAPI.<PersonDTO>builder().friendlyMessagesList(new ArrayList<>())
				.build();

		try {
			checkAddressFlow.execute(personDTO.getAddress());
			checkPersonFlow.execute(personDTO);

			response.setData(insertPersonFlowItem.insert(personDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
			throw e;
		}

		return response;
	}
}
