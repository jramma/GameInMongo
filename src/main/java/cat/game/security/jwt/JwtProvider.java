package cat.game.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import cat.game.security.service.UsuarioDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private int expiration;

	public String generateToken(Authentication authentication) {
		UsuarioDetails userDetails = (UsuarioDetails) authentication.getPrincipal();
		return Jwts.builder().signWith(getKey(secret)).setSubject(userDetails.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration * 1000)).claim("roles", getRoles(userDetails))
				.compact();

	}

	public String getUsernameFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(getKey(secret)).build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token) {

		boolean validToken = false;
		try {
			Jwts.parserBuilder().setSigningKey(getKey(secret)).build().parseClaimsJws(token).getBody();
			validToken = true;
		} catch (SignatureException e) {
			logger.error(e.toString());
		} catch (ExpiredJwtException e) {
			logger.error(e.toString());
		} catch (UnsupportedJwtException e) {
			logger.error(e.toString());
		} catch (MalformedJwtException e) {
			logger.error(e.toString());
		} catch (IllegalArgumentException e) {
			logger.error(e.toString());
		} catch (Exception e) {
			logger.error(e.toString());
		}

		return validToken;
	}

	// Esto igual debería devolver una
	// lista de roles o devuelve una lista de roles
	private List<String> getRoles(UsuarioDetails usuarioDetails) {
		return (List<String>) usuarioDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
	}

	private Key getKey(String secret) {
		byte[] secretBytes = Decoders.BASE64URL.decode(secret);
		return Keys.hmacShaKeyFor(secretBytes);
	}
}
