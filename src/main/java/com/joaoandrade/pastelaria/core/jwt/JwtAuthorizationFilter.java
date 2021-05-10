package com.joaoandrade.pastelaria.core.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private JwtUtil jwtUtil;
	private UserDetailsService userDetailsService;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
			UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String tokenJwt = request.getHeader("Authorization");

		if (StringUtils.hasLength(tokenJwt) && tokenJwt.startsWith("Bearer ")) {
			tokenJwt = tokenJwt.replaceAll("Bearer ", "");

			if (jwtUtil.isTokenJwtValido(tokenJwt)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthentication(tokenJwt);

				if (usernamePasswordAuthenticationToken != null) {
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}

		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String tokenJwt) {
		String email = jwtUtil.getEmail(tokenJwt);

		if (StringUtils.hasLength(email)) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);

			if (userDetails != null) {
				return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			}
		}

		return null;
	}

}
