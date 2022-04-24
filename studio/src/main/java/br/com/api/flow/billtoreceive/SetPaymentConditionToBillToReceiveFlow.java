package br.com.api.flow.billtoreceive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.BillToReceiveDTO;
import br.com.api.entity.repository.BillToReceiveFilter;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.billtoreceive.item.BuildInstallmentsToReceiveFlowItem;
import br.com.api.flow.billtoreceive.item.FindBillToReceiveByFilterFlowItem;
import br.com.api.flow.billtoreceive.item.SetPaymentConditionToReceiveFlowItem;
import br.com.api.flow.paymentcondition.item.CheckAndGetPaymentConditionFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;
import lombok.var;

@Service
public class SetPaymentConditionToBillToReceiveFlow {

	@Autowired
	private FindBillToReceiveByFilterFlowItem findBillToReceiveByFilterFlowItem;

	@Autowired
	private SetPaymentConditionToReceiveFlowItem setPaymentConditionFlowItem;
	@Autowired
	private CheckAndGetPaymentConditionFlowItem checkAndGetPaymentConditionFlowItem;

	@Autowired
	private BuildInstallmentsToReceiveFlowItem buildInstallmentsFlowItem;

	@Transactional
	public ResponseAPI execute(Integer billToReceiveIdentifier, Integer paymentConditionIdentifier,
			HttpHeaders headers) {

		ResponseAPI response = ResponseAPI.builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			final var existing = findBillToReceiveByFilterFlowItem.findByFilter(BillToReceiveFilter.builder()
					.columnList(Arrays.asList("identifier", "value", "paymentCondition.identifier")).resultUnique(true)
					.example(BillToReceiveDTO.builder().identifier(billToReceiveIdentifier).build()).build())
					.getResult().stream().findFirst();

			if (existing.isPresent()) {
				if (Objects.isNull(existing.get().getPaymentCondition())) {
					final var paymentConditionDTO = checkAndGetPaymentConditionFlowItem
							.checkAndGet(paymentConditionIdentifier);
					buildInstallmentsFlowItem.build(existing.get(), paymentConditionDTO);
					setPaymentConditionFlowItem.update(billToReceiveIdentifier, paymentConditionIdentifier);
					response.setStatus(StatusResponse.SUCCESS);
					return response;
				} else {
					response.setStatus(StatusResponse.ERROR);
					response.setReportTech(ReportTech.builder().level(LevelReport.WARN).code("003")
							.message("Título já possui condicao de pagamento, nao é possível alterá-lo!").build());
				}
			}

			response.setStatus(StatusResponse.ERROR);
			response.setReportTech(
					ReportTech.builder().level(LevelReport.WARN).code("003").message("Título não encontrado!").build());
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}

}
