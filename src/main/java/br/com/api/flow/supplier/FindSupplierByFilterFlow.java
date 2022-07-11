package br.com.api.flow.supplier;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.entity.repository.SupplierFilter;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.supplier.item.FindSupplierByFilterFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class FindSupplierByFilterFlow {

	@Autowired
	private FindSupplierByFilterFlowItem findSupplierByFilterFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI<SupplierFilter> execute(SupplierFilter filter, HttpHeaders headers) {

		ResponseAPI<SupplierFilter> response = ResponseAPI.<SupplierFilter>builder()
				.friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(findSupplierByFilterFlowItem.findByFilter(filter));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
