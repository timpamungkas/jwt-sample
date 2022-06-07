package com.example.jwt;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JwtService {

	private static final String HMAC_SECRET = "theHmacSecretKeyForJwt";

	private static final String SUBJECT = "documentum";

	private static final String ISSUER = "bfi.co.id";

	private static final String DOCUMENT_ID = "docId";

	public String create(JwtData jwtData) {
		var algorithm = Algorithm.HMAC256(HMAC_SECRET);
		var expiresAt = LocalDateTime.now(ZoneOffset.UTC).plusSeconds(30);

		return JWT.create().withSubject(SUBJECT).withIssuer(ISSUER).withClaim(DOCUMENT_ID, jwtData.getDocumentId())
				.withExpiresAt(Date.from(expiresAt.toInstant(ZoneOffset.UTC))).sign(algorithm);
	}

	public Optional<JwtData> read(String jwtToken, String docId) {
		try {
			var algorithm = Algorithm.HMAC256(HMAC_SECRET);
			var jwtVerifier = JWT.require(algorithm).withClaim(DOCUMENT_ID, docId).withSubject(SUBJECT)
					.withIssuer(ISSUER).acceptExpiresAt(10).build();

			var decodedJwt = jwtVerifier.verify(jwtToken);

			var jwtData = new JwtData();
			jwtData.setDocumentId(decodedJwt.getClaim(DOCUMENT_ID).asString());

			return Optional.of(jwtData);
		} catch (JWTVerificationException ex) {
			return Optional.empty();
		}
	}

}
