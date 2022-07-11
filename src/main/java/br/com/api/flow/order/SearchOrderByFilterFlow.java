package br.com.api.flow.order;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.entity.repository.OrderFilter;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.order.item.SearchOrderByFilterFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class SearchOrderByFilterFlow {

	@Autowired
	private SearchOrderByFilterFlowItem searchOrderByFilterFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI<OrderFilter> execute(OrderFilter filter, HttpHeaders headers) {

		ResponseAPI<OrderFilter> response = ResponseAPI.<OrderFilter>builder().friendlyMessagesList(new ArrayList<>())
				.build();

		try {
			if (Boolean.TRUE.equals(filter.getCustomSearch())) {
				response.setData(searchOrderByFilterFlowItem.findByFilterImpl(filter));
			} else {
				response.setData(searchOrderByFilterFlowItem.findByFilter(filter));
			}
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
