package br.com.api.flow.payroll.item;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.dto.ClassDTO;
import br.com.api.dto.EmployeeDTO;
import br.com.api.dto.MatriculationDTO;
import br.com.api.dto.PayrollDTO;
import br.com.api.dto.PayrollDetailDTO;
import br.com.api.entity.repository.EmployeeFilter;
import br.com.api.enums.EmployeeStatusEnum;
import br.com.api.enums.OftenEnum;
import br.com.api.enums.PayrollStatusEnum;
import br.com.api.enums.TypeOfPaymentEnum;
import br.com.api.flow.classes.item.SearchClassByEndDateGreatherThanFlowItem;
import br.com.api.flow.classes.item.SearchClassByFilterFlowItem;
import br.com.api.flow.employee.item.SearchEmployeeByFilterFlowItem;
import br.com.api.flow.matriculationitem.item.SearchContainingClassByFilterFlowItem;
import br.com.api.utils.ResponseAPI;
import br.com.api.utils.Utils;

@Service
public class ProcessPayrollFlowItem {

	@Autowired
	private SearchEmployeeByFilterFlowItem searchEmployeeByFilterFlowItem;
	@Autowired
	private SearchClassByFilterFlowItem searchClassByFilterFlowItem;
	@Autowired
	private SearchClassByEndDateGreatherThanFlowItem searchClassByEndDateGreatherThanFlowItem;
	@Autowired
	private SearchContainingClassByFilterFlowItem searchContainingClassByFilterFlowItem;
	@Autowired
	private InsertPayrollFlowItem insertPayrollFlowItem;

	public ResponseEntity<ResponseAPI<PayrollDTO>> generate(PayrollDTO payrollDTO) {

		ResponseAPI<PayrollDTO> response = ResponseAPI.<PayrollDTO>builder().friendlyMessagesList(new ArrayList<>())
				.build();

		payrollDTO.setPayrollDetailList(regularPayroll(payrollDTO));
		payrollDTO.getPayrollDetailList().addAll(classPayroll(payrollDTO));

		payrollDTO = insertPayrollFlowItem.insert(payrollDTO);
		
		response.setData(payrollDTO);
		return ResponseEntity.ok(response);
	}

	private List<PayrollDetailDTO> regularPayroll(PayrollDTO payrollDTO) {

		List<EmployeeDTO> employeeList = searchEmployeeByFilterFlowItem
				.search(EmployeeFilter.builder().example(EmployeeDTO.builder().typeOfSalary(TypeOfPaymentEnum.BY_MONTH)
						.status(EmployeeStatusEnum.IN_COMPANY).build()).build())
				.getResult();

		List<PayrollDetailDTO> payrollDetailList = new ArrayList<>();
		employeeList.forEach(employee -> {
			PayrollDetailDTO payrollDetail = PayrollDetailDTO.builder()
					.description(employee.getIdentifier() + "_SAL_" + payrollDTO.getInitDate().format(DateTimeFormatter.ofPattern("MM/yyyy")))
					.employee(employee).value(employee.getSalaryValue()).build();
			payrollDetailList.add(payrollDetail);
		});

		return payrollDetailList;
	}

	private List<PayrollDetailDTO> classPayroll(PayrollDTO payrollDTO) {

		List<ClassDTO> classList = searchClassByEndDateGreatherThanFlowItem.search(Calendar.getInstance());

		List<PayrollDetailDTO> payrollDetailList = new ArrayList<>();
		classList.forEach(clazz -> {

			List<LocalDate> daysOfClass = new ArrayList<>();
			Map<String, Long> daysAndTotalTimeOfClass = new HashMap<String, Long>();

			clazz.getScheduleDetailClassList().forEach(scheduleDetailClass -> {
				if (scheduleDetailClass.getOften() == OftenEnum.WEEKLY) {
					for (LocalDate d : Utils.between(payrollDTO.getInitDate(), payrollDTO.getEndDate())) {
						if (Objects.equals(d.getDayOfWeek().name(), scheduleDetailClass.getOftenDay())) {
							daysOfClass.add(d);

							daysAndTotalTimeOfClass.putIfAbsent(d.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
									ChronoUnit.MINUTES.between(scheduleDetailClass.getInitTime(),
											scheduleDetailClass.getEndTime()));
						}
					}
				}

				if (scheduleDetailClass.getOften() == OftenEnum.MONTHLY) {
					int day = Integer.parseInt(scheduleDetailClass.getOftenDay());
					daysOfClass.add(
							LocalDate.of(payrollDTO.getInitDate().getYear(), payrollDTO.getInitDate().getMonth(), day));
					daysAndTotalTimeOfClass.putIfAbsent(scheduleDetailClass.getOftenDay(), ChronoUnit.MINUTES
							.between(scheduleDetailClass.getInitTime(), scheduleDetailClass.getEndTime()));
				}
			});

			Map<EmployeeDTO, Double> valueByEmployee = new HashMap<>();

			clazz.getRolePaymentList().forEach(role -> {
				Long quantityMatriculation = searchContainingClassByFilterFlowItem.count(clazz);

				if (quantityMatriculation >= role.getSinceStudentNumber()) {
					if (role.getUntilStudentNumber() == 0 || quantityMatriculation < role.getUntilStudentNumber()) {
						if (role.getTypeOfPayment() == TypeOfPaymentEnum.BY_MONTH) {
							valueByEmployee.put(role.getEmployee(),
									valueByEmployee.getOrDefault(role.getEmployee(), 0D) + role.getPaymentValue());
						}

						if (Objects.equals(role.getTypeOfPayment(), TypeOfPaymentEnum.BY_PERCENT)) {
							List<MatriculationDTO> matriculationList = searchContainingClassByFilterFlowItem
									.search(clazz);

							matriculationList.forEach(matriculation -> {

								matriculation.getMatriculationItemList().forEach(item -> {
									if (Objects.equals(item.getProduct(), clazz.getProduct())) {
										Double value = item.getTotalPrice() * role.getPaymentValue() / 100;

										valueByEmployee.put(role.getEmployee(),
												valueByEmployee.getOrDefault(role.getEmployee(), 0D) + value);
									}
								});
							});
						}

						if (Objects.equals(role.getTypeOfPayment(), TypeOfPaymentEnum.BY_HOUR)) {
							Long totalTime = daysAndTotalTimeOfClass.values().stream().reduce(Long::sum).orElse(0L);
							Double value = role.getPaymentValue() * (totalTime / 60D);

							valueByEmployee.put(role.getEmployee(),
									valueByEmployee.getOrDefault(role.getEmployee(), 0D) + value);
						}
					}
				}

			});

			valueByEmployee.entrySet().forEach(employeeData -> {
				PayrollDetailDTO payrollDetail = PayrollDetailDTO.builder().employee(employeeData.getKey())
						.description(employeeData.getKey().getIdentifier() + "_" + clazz.getDescription() + "_"
								+ payrollDTO.getInitDate().format(DateTimeFormatter.ofPattern("MM/yyyy")))
						.value(employeeData.getValue()).build();

				payrollDetailList.add(payrollDetail);
			});
		});

		return payrollDetailList;
	}
}
