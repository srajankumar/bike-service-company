package com.cts.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.cts.exceptions.BikeServiceException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

// Handles JWT token creation and validation for user authentication
@Component
public class JwtTokenProvider {

	@Value("${app.jwt.secret}")
	private String secretKey;

	@Value("${app.jwt.expiry-millis}")
	private long expiryMillis;

	// Generates a JWT token for a user after successful authentication
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expiryDate = new Date(currentDate.getTime() + expiryMillis);

		String token = Jwts.builder().subject(username).issuedAt(currentDate).expiration(expiryDate).signWith(key())
				.compact();
		return token;
	}

	// Retrieves the signing key for token verification
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}

	// Extracts the username from a given JWT token
	public String getUsername(String token) {

		String username = Jwts.parser()
				.verifyWith((SecretKey) key())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
		return username;
	}

	// Validates a JWT token to ensure it is correctly formed and not expired
	public boolean validateToken(String token) {

		try {
			Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
			return true;
		} catch (MalformedJwtException | SignatureException ex) {
			throw new BikeServiceException(HttpStatus.BAD_REQUEST, "Invalid token");
		} catch (ExpiredJwtException ex) {
			throw new BikeServiceException(HttpStatus.BAD_REQUEST, "Expired token");
		} catch (UnsupportedJwtException ex) {
			throw new BikeServiceException(HttpStatus.BAD_REQUEST, "Unsupported token");
		} catch (IllegalArgumentException ex) {
			throw new BikeServiceException(HttpStatus.BAD_REQUEST, "Token claims string is null or empty");
		}
	}
}
