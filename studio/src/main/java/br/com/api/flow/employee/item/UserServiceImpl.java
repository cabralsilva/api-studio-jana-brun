package br.com.api.flow.employee.item;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.api.dto.security.Credential;
import br.com.api.dto.security.UserDetail;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.jwt.JwtTokenProvider;
import br.com.api.utils.ResponseAPI;

@Service
public class UserServiceImpl {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	protected JwtTokenProvider tokenProvider;

	public ResponseEntity<ResponseAPI<UserDetail>> authentication(Credential credential) {

		ResponseAPI<UserDetail> response = ResponseAPI.<UserDetail>builder().friendlyMessagesList(new ArrayList<>())
				.build();
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(credential.getUsername(), credential.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			response.setData(tokenProvider.generateToken(authentication));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);
			response.setFriendlyMessagesList(Arrays.asList("Usuário/Senha inválido!"));
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}

		return ResponseEntity.ok(response);
	}
}
