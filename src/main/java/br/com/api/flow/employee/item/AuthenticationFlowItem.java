package br.com.api.flow.employee.item;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import br.com.api.converter.EmployeeMapper;
import br.com.api.dto.EmployeeDTO;
import br.com.api.dto.security.Credential;
import br.com.api.entity.Employee;
import br.com.api.entity.repository.EmployeeRepository;
import br.com.api.utils.MD5PasswordEncoder;

@Component
public class AuthenticationFlowItem {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeMapper employeeMapper;

	public Optional<EmployeeDTO> authenticate(Credential credentials) {

		if (credentials.getPassword() != null) {
			credentials.setPassword(new MD5PasswordEncoder().encode(credentials.getPassword()));
		}

		Example<Employee> example = Example.of(employeeMapper.toEntity(credentials));
		final Optional<Employee> ret = employeeRepository.findOne(example);

		if (ret.isPresent()) {
			return Optional.of(employeeMapper.toDTO(ret.get()));
		}

		return Optional.empty();
	}
}
