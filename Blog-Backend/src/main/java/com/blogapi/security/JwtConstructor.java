package com.blogapi.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtConstructor {

	private static final long JWT_TOKEN_VALIDITY = 5*60*60;
	//sample - random key // you can give any random key 
	private  String JWT_SECRET_KEY = "2H9JdC5Hu0kL9j7V7G4Z5K8O2lM0V4Y8pR9xT6yC3xA=";
	
	public String generateUsernameFromToken(String token) 
	{
		return getClaimFromToken(token,Claims::getSubject);
	}
	
	public <T> T getClaimFromToken(String token,Function<Claims,T> claimsResolver) 
	{
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody();
	}
	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token,Claims::getExpiration);
	}
	
	public String genToken(UserDetails userDetails) 
	{
		Map<String,Object> claims = new HashMap<>();
		return doGenerate(claims,userDetails.getUsername());
	}

	private String doGenerate(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS256,JWT_SECRET_KEY)
				.compact();
	}
	
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = generateUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
}
