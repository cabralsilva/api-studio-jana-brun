package br.com.api.flow.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.api.dto.security.UserDetail;
import br.com.api.utils.Utils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Transactional
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider tokenProvider;

//	@Autowired
//	private SessionService sessionService;

	@Autowired
	private MessageSource messageSource;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {

//			sessionService.verifyStatusService();

			String jwt = Utils.getJwtFromRequest(request);

			if (StringUtils.hasText(jwt)) {
				tokenProvider.validateToken(jwt);
				String userJSON = tokenProvider.getUserJSONFromJWT(jwt);
				ObjectMapper objectMapper = new ObjectMapper();
				UserDetail userDetails = objectMapper.readValue(userJSON, UserDetail.class);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);

//				if (sessionService.isValidSessionFromToken(userDetails.getSession().getIdentifier()))
//					sessionService.refreshByIdentifier2(userDetails.getSession().getIdentifier());
//				else
//					throw new ValidationException(ValidationExceptionCodeEnum.APIAUTHENTICATION_INVALID_SESSION);

			} else {

				response.sendError(HttpStatus.UNAUTHORIZED.value(),
						messageSource.getMessage("exception.validation.token.invalid", null, request.getLocale()));
				return;
			}

		} catch (io.jsonwebtoken.SignatureException ex) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(),
					messageSource.getMessage("exception.validation.token.invalid.signed", null, request.getLocale()));
			return;
		} catch (MalformedJwtException ex) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(),
					messageSource.getMessage("exception.validation.token.invalid", null, request.getLocale()));
			return;
		} catch (ExpiredJwtException ex) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(),
					messageSource.getMessage("exception.validation.token.expired", null, request.getLocale()));
			return;
		} catch (UnsupportedJwtException ex) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(),
					messageSource.getMessage("exception.validation.token.unsupported", null, request.getLocale()));
			return;
		} catch (IllegalArgumentException ex) {
//			response.sendError(HttpStatus.UNAUTHORIZED.value(),
//					messageSource.getMessage("exception.validation.token.empty", null, request.getLocale()));
//		} catch (ValidationException ex) {
//			if (ex.getCode().equals(ValidationExceptionCodeEnum.GLOBAL_VALIDATION_SYSTEM_IN_MAINTENENCE)) {
//				response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(),
//						messageSource.getMessage(ex.getCode().getI18n(), null, request.getLocale()));
//				return;
//			}

			response.sendError(HttpStatus.UNAUTHORIZED.value(),
					messageSource.getMessage("exception.global", null, request.getLocale()));
			return;
		} catch (Exception ex) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(),
					messageSource.getMessage("exception.global", null, request.getLocale()));
			return;
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();

		boolean ret = false;
		if (path.contains("/auth"))
			ret = true;
		return true;
	}
}
