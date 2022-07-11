package br.com.api.flow.supplier;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.PersonDTO;
import br.com.api.dto.SupplierDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.person.UpdatePersonFlow;
import br.com.api.flow.supplier.item.UpdateSupplierFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdateSupplierFlow {

	@Autowired
	private UpdateSupplierFlowItem updateSupplierFlowItem;
	@Autowired
	private UpdatePersonFlow updatePersonFlow;

	public ResponseAPI<SupplierDTO> execute(SupplierDTO supplierDTO, HttpHeaders headers) {

		ResponseAPI<SupplierDTO> response = ResponseAPI.<SupplierDTO>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			supplierDTO.setPerson(PersonDTO.builder()
					.identifier(updatePersonFlow.execute(supplierDTO.getPerson(), headers).getData().getIdentifier())
					.build());
			response.setData(updateSupplierFlowItem.update(supplierDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
