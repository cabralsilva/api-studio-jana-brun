package br.com.api.dto.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.dto.EmployeeDTO;
import br.com.api.enums.RoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserDetail implements UserDetails {

	private static final long serialVersionUID = 1L;
	private String username;
	@JsonIgnore
	private String password;
	private String token;
	private List<GrantedAuthority> authorities;
	private EmployeeDTO employee;

	public UserDetail(EmployeeDTO employee) {
		this.username = employee.getEmail();
		this.password = employee.getPassword();
		this.employee = employee;
		if (ObjectUtils.isNotEmpty(employee.getRoleList())) {
			List<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
			for (RoleEnum role : employee.getRoleList()) {
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.name());
				grantedAuthoritiesList.add(grantedAuthority);
			}
			this.authorities = grantedAuthoritiesList;
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
