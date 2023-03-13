package com.konkuk.vocabulary.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity implements UserDetails {
	
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	private Long userKey;
	
	@Column(length=50 , nullable = false)
	private String id;
	
	@Column(length=50 , nullable = false)
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
    private List<String> roles = new LinkedList<String>();
	
	/***
	 * 해당 구간부터는 UserDetails 영역
	 */
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		
		for(String role : roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return id;
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
