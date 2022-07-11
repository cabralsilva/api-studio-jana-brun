package br.com.api.flow.order;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.dto.OrderDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.OrderStatusEnum;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.order.item.InsertOrderFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertOrderFlow {

	@Autowired
	private InsertOrderFlowItem insertOrderFlowItem;

	public ResponseEntity<ResponseAPI<OrderDTO>> execute(OrderDTO orderDTO, HttpHeaders headers) {

		ResponseAPI<OrderDTO> response = ResponseAPI.<OrderDTO>builder().friendlyMessagesList(new ArrayList<>())
				.build();

		try {
			orderDTO.setStatus(OrderStatusEnum.DONE);
			response.setData(insertOrderFlowItem.insert(orderDTO));
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
