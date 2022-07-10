package br.com.api.flow.jwt;

import java.io.Serializable;

import br.com.api.dto.EmployeeDTO;

public class JwtAuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 8406078027760962915L;

	private String tokenType = "Bearer";
	private String accessToken;
	private EmployeeDTO user;

	public JwtAuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public EmployeeDTO getUser() {
		return user;
	}

	public void setUser(EmployeeDTO user) {
		this.user = user;
	}

}
