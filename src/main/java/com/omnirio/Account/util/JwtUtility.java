package com.omnirio.Account.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtility {

	private static String SECRET_KEY = "secretKey";

	public static String generateJWTToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 5 * 1000 * 60 * 60))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public static boolean validToken(String token, UserDetails userDetail) {
		String userName = extractUserName(token);
		return userName.equals(userDetail.getUsername()) && !isTokenExpired(token);
	}

	public static boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public static String extractUserName(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	public static Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}

	public static <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public static Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
}
