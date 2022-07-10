package br.com.api.flow.employee.item;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.EmployeeDTO;
import br.com.api.dto.security.Credential;
import br.com.api.dto.security.UserDetail;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private AuthenticationFlowItem authenticationEmployee;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<EmployeeDTO> employee = authenticationEmployee
				.authenticate(Credential.builder().username(username).build());

		if (employee.isPresent()) {
			return new UserDetail(employee.get());
		}
		return null;
	}

}
