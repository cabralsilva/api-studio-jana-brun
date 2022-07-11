package br.com.api.flow.supplier;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.PersonDTO;
import br.com.api.dto.SupplierDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.person.InsertPersonFlow;
import br.com.api.flow.supplier.item.InsertSupplierFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertSupplierFlow {

	@Autowired
	private InsertSupplierFlowItem insertSupplierFlowItem;
	@Autowired
	private InsertPersonFlow insertPersonFlow;

	@Autowired
	private MessageSource messageSource;

	@Transactional
	public ResponseAPI<SupplierDTO> execute(SupplierDTO supplierDTO, HttpHeaders headers) {

		ResponseAPI<SupplierDTO> response = ResponseAPI.<SupplierDTO>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			supplierDTO.setPerson(PersonDTO.builder()
					.identifier(insertPersonFlow.execute(supplierDTO.getPerson(), headers).getData().getIdentifier())
					.build());
			response.setData(insertSupplierFlowItem.insert(supplierDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
