package com.oxyl.NewroFactory.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

	private int id;
	private String username;
	private String password;
	private Role role;
	private boolean enabled;

	private User(UserBuilder builder) {
		this.id = builder.id;
		this.username = builder.username;
		this.password = builder.password;
		this.role = builder.role;
		this.enabled = builder.enabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(password, other.password) && role == other.role
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [id="+id+", username=" + username + ", password=" + password + ", role=" + role + ", enabled= "+enabled+"]";
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.name());
		return Collections.singleton(authority);
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
		return enabled;
	}

	public static class UserBuilder {
		private int id;
		private String username;
		private String password;
		private Role role;
		private boolean enabled;

		public UserBuilder() {
		}
		
		public UserBuilder id(int id) {
			this.id = id;
			return this;
		}

		public UserBuilder username(String username) {
			this.username = username;
			return this;
		}

		public UserBuilder password(String password) {
			this.password = password;
			return this;
		}

		public UserBuilder role(Role role) {
			this.role = role;
			return this;
		}
		
		public UserBuilder enabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public User build() {
			User user = new User(this);
			return user;
		}
	}
}
