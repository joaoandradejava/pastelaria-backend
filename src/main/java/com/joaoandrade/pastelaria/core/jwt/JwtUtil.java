package com.joaoandrade.pastelaria.core.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Autowired
	private JwtConfigProperty jwtConfigProperty;

	public String gerarTokenJwt(String email) {
		return Jwts.builder().setSubject(email)
				.setExpiration(new Date(System.currentTimeMillis() + jwtConfigProperty.getTempoDeExpiracaoDoTokenJwt()))
				.signWith(SignatureAlgorithm.HS512, jwtConfigProperty.getSenhaDeCriptografiaDoTokenJwt().getBytes())
				.compact();
	}

	public boolean isTokenJwtValido(String tokenJwt) {
		Claims claims = getClaims(tokenJwt);

		if (claims != null) {
			String email = claims.getSubject();
			Date dataDeExpiracao = claims.getExpiration();
			Date dataDeAgora = new Date();

			if (StringUtils.hasLength(email) && dataDeExpiracao != null && dataDeAgora.before(dataDeExpiracao)) {
				return true;
			}
		}

		return false;
	}

	private Claims getClaims(String tokenJwt) {
		try {
			return Jwts.parser().setSigningKey(jwtConfigProperty.getSenhaDeCriptografiaDoTokenJwt().getBytes())
					.parseClaimsJws(tokenJwt).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public String getEmail(String tokenJwt) {
		Claims claims = getClaims(tokenJwt);

		return claims != null ? claims.getSubject() : null;
	}

}
