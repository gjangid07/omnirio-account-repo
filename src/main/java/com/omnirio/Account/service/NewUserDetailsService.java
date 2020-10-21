package com.omnirio.Account.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.omnirio.Account.model.User;

@Service
public class NewUserDetailsService implements UserDetailsService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		String getUserUrl = "http://OMNIRIO-CUSTOMER-SERVICE/customer/user/" + username;
		User newUser = restTemplate.getForObject(getUserUrl, User.class);
		return new org.springframework.security.core.userdetails.User(newUser.getUserName(), "abcd", new ArrayList<>());
	}

}
