package br.com.api.flow.product;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.ProductDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.product.item.UpdateProductFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdateProductFlow {

	@Autowired
	private UpdateProductFlowItem updateProductFlowItem;

	public ResponseAPI<ProductDTO> execute(ProductDTO productDTO, HttpHeaders headers) {

		ResponseAPI<ProductDTO> response = ResponseAPI.<ProductDTO>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(updateProductFlowItem.update(productDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
