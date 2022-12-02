package com.oxyl.NewroFactory.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.oxyl.NewroFactory.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

	private UserService userService;
	private JwtTokenUtil jwtTokenUtil;
	public JwtRequestFilter(UserService userService, JwtTokenUtil jwtTokenUtil) {
		this.userService = userService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
	    String username = null;
	    String jwtToken = null;

	    
	    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
	        jwtToken = requestTokenHeader.substring(7);
	        try {
	            username = jwtTokenUtil.getUsername(jwtToken);
	        } catch (IllegalArgumentException e) {
	            System.out.println("Unable to get JWT Token");
	        } catch (ExpiredJwtException e) {
	            System.out.println("JWT Token has expired");
	        } catch (MalformedJwtException mje) {
	        	System.out.println("Invalid Token");
	        }
	    } else {
	        logger.warn("JWT Token does not begin with Bearer String");
	    }
	    
	    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	        UserDetails userDetails = this.userService.loadUserByUsername(username);
	        
	        if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
	            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	            
	            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	        }
	    }
	    filterChain.doFilter(request, response);
		
	}

		
}
