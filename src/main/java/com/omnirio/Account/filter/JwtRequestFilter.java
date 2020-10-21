package com.omnirio.Account.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.omnirio.Account.service.NewUserDetailsService;
import com.omnirio.Account.util.JwtUtility;

public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private NewUserDetailsService userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");

		String jwtTokenString = null;
		String userName = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
			jwtTokenString = authorizationHeader.substring(7);
			userName = JwtUtility.extractUserName(jwtTokenString);
		}

		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userdetails = userDetailService.loadUserByUsername(userName);
			if (JwtUtility.validToken(jwtTokenString, userdetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userdetails, null, userdetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
