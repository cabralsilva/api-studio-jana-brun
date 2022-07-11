package br.com.api.flow.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import br.com.api.dto.EmployeeDTO;
import br.com.api.dto.security.UserDetail;
import br.com.api.utils.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("${app.security.jwt.secret}")
	private String jwtSecret;

	@Value("${app.security.jwt.expiration.in.ms}")
	private int jwtExpirationInMs;

	public UserDetail generateToken(Authentication authentication) {

		UserDetail userPrincipal = (UserDetail) authentication.getPrincipal();
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
		String token = Jwts.builder().setSubject(Utils.toStringJSON(userPrincipal)).setIssuedAt(new Date())
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		userPrincipal.setToken(token);

		return userPrincipal;
	}

	public String generateTokenResetPassword(EmployeeDTO userDTO) {

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + Utils.TIME_IN_MILLISECONDS_30_MIN);

		return Jwts.builder().setSubject(Utils.toStringJSON(userDTO)).setIssuedAt(new Date())
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

	}

	public String getUserJSONFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	public void validateToken(String authToken) {
		Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
	}
}
