package br.com.api.flow.employee.item;

import java.util.Optional;

import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.api.dto.EmployeeDTO;
import br.com.api.entity.repository.EmployeeRepository;
import br.com.api.exceptions.FindByFilterException;
import br.com.api.exceptions.FindByFilterExceptionEnum;

@Component
public class UpdatePasswordFlowItem {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private GetEmployeeByIdFlowItem getEmployeeByIdFlowItem;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder encoder;

	public void updatePassword(EmployeeDTO target) throws Exception {

		Optional<EmployeeDTO> employeeDTO = getEmployeeByIdFlowItem.get(target.getIdentifier());

		if (!employeeDTO.isPresent()) {
			throw new FindByFilterException(FindByFilterExceptionEnum.RESOURCE_NOT_FOUND);
		}

		validator(target, employeeDTO.get());
		target.setNewPassword(encoder.encode(target.getNewPassword()));
		employeeRepository.updatePassword(target.getNewPassword(), target.getIdentifier());
	}

	private void validator(EmployeeDTO source, EmployeeDTO current) throws ValidationException {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(current.getEmail(), source.getPassword()));
		} catch (BadCredentialsException e) {
			throw new ValidationException("Senha atual incorreta!");
		} catch (Exception e) {
			throw new ValidationException("Senha atual incorreta!");
		}
	}
}
