package br.com.api.flow.pricetableitem;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.dto.MatriculationItemDTO;
import br.com.api.dto.PriceTableDTO;
import br.com.api.dto.PriceTableItemDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.pricetable.item.GetPriceTableEffectiveFlowItem;
import br.com.api.flow.pricetableitem.item.GetPriceTableItemByCartItemFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class GetPriceTableItemByCartItemFlow {

	@Autowired
	private GetPriceTableEffectiveFlowItem getPriceTableEffectiveFlowItem;
	@Autowired
	private GetPriceTableItemByCartItemFlowItem getPriceTableItemByCartItemFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseEntity<ResponseAPI<PriceTableItemDTO>> execute(MatriculationItemDTO matriculationItem,
			HttpHeaders headers) {

		ResponseAPI<PriceTableItemDTO> response = ResponseAPI.<PriceTableItemDTO>builder()
				.friendlyMessagesList(new ArrayList<>()).build();

		try {
			Optional<PriceTableDTO> priceTableDTO = getPriceTableEffectiveFlowItem.get();

			if (!priceTableDTO.isPresent()) {
				throw new Exception("Não existe tabela de preço vigente");
			}

			Optional<PriceTableItemDTO> priceTableItemDTO = getPriceTableItemByCartItemFlowItem.get(matriculationItem,
					priceTableDTO.get().getItemList());
			
			if (!priceTableItemDTO.isPresent()) {
				throw new Exception("Preço não localizado para este produto");
			}

			response.setData(priceTableItemDTO.get());

			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
			response.getFriendlyMessagesList().add(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
		}

		return ResponseEntity.ok(response);
	}
}
