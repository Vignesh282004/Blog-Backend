package com.blogapi.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilters extends OncePerRequestFilter{
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtConstructor jwtConstructor;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		String username = null;
		String token= null;
		
		System.out.println(header);
		
		if(header !=null && header.startsWith("Bearer")) {
			token = header.substring(7);
			try {
				username = this.jwtConstructor.generateUsernameFromToken(token);
				
			} catch (IllegalArgumentException e) {
				System.out.println("uanble to get jwt ");
				
			} catch (ExpiredJwtException e) {
				
				System.out.println("token is expired");
			}
			catch (MalformedJwtException e) {
				
				System.out.println("invalid jwt token");
			}
		}
		else {
			System.out.println("Jwt not starts with bearer ....... Plz fix it");
		}
		
		//if we got token correctly we will validate it
		
			//System.out.println("username :" + username);
			//System.out.println("token  : " + token);
			//System.out.println(header);
		//validation code for token 
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				if(this.jwtConstructor.validateToken(token, userDetails))
				{
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(token,null,userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()	.buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
				else {
					System.out.println("invalid jwt token..!");
				}
			}
			else {
					System.out.println("username is null or context is null....");
			}
			filterChain.doFilter(request, response);
	}	
}
