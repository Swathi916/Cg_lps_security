package com.capgemini.lps.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.capgemini.lps.entity.User;

@Component
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
  
	private User user;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		return Arrays.asList(authority);
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	public User getUser() {
		return user;
	}

	public int getUserId() {
		return user.getUserId();
	}
	public String getEmail() {
		return user.getEmail();
	}
	public String getRole() {
		return user.getRole();
	}
	public String getFullName() {
		return user.getFullName();
	}


	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String getUsername() {
		return user.getEmail();
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
