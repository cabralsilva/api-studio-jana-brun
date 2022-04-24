package br.com.api.flow.product;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.entity.repository.ProductFilter;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.product.item.FindProductByFilterFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class FindProductByFilterFlow {

	@Autowired
	private FindProductByFilterFlowItem findProductByFilterFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI<ProductFilter> execute(ProductFilter filter, HttpHeaders headers) {

		ResponseAPI<ProductFilter> response = ResponseAPI.<ProductFilter>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(findProductByFilterFlowItem.findByFilter(filter));
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
