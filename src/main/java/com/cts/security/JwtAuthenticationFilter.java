package com.cts.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cts.exceptions.BikeServiceException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Filters incoming requests to verify JWT tokens and authenticate users
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	// Checks if the request contains a valid token, authenticates the user, and sets the security context
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
		String token = getTokenFromRequest(request);
		
		if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			
			String username = jwtTokenProvider.getUsername(token);
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
		} catch(BikeServiceException e){
			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.getWriter().write("{\"error\": " + e.getMessage() + "}");
			return;
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	// Extracts the JWT token from the request header
	private String getTokenFromRequest(HttpServletRequest request) {
		
		String bearerToken = request.getHeader("Authorization");
		
		// Ensures the token starts with "Bearer " before extracting it
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			String token = bearerToken.substring(7);
			return token;
		}
		
		return null;
	}
	
}
