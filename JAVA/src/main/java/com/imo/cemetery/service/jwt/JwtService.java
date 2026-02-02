package com.imo.cemetery.service.jwt;

import com.imo.cemetery.model.entity.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Jwt service.
 */
@Service
public class JwtService {

    private final JwtBuilder jwtBuilder;
    private final SecretKey secretKey; // Clave secreta para firmar el token

    /**
     * Instantiates a new Jwt service.
     *
     * @param secretKey the secret key
     */
    public JwtService(SecretKey secretKey) {
        this.secretKey = secretKey;
        this.jwtBuilder = Jwts.builder();
    }

    /**
     * Generate token string.
     *
     * @param user the user
     * @return the string
     */
    public String generateToken(User user) {
        Instant now = Instant.now();
        Date expiryDate = Date.from(now.plus(24, ChronoUnit.HOURS));

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", List.of(user.getRole().getTipo()));

        return jwtBuilder
                // email del usuario
                .subject(String.valueOf(user.getEmail()))
                // La clave secreta para firmar el token y saber que es nuestro cuando lleguen las peticiones del frontend
                .signWith(secretKey, SignatureAlgorithm.HS256)
                // Fecha emisión del token
                .issuedAt(Date.from(now))
                // Fecha de expiración del token
                .expiration(expiryDate)
                // información personalizada: rol o roles, username, email, avatar...
                // .claim("role", user.getRole())
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().getTipo())
                //.claim("avatar", user.getAvatarUrl())
                // Construye el token
                .compact();


    }

    /**
     * Extract username string.
     *
     * @param token the token
     * @return the string
     */
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Is token valid boolean.
     *
     * @param token       the token
     * @param userDetails the user details
     * @return the boolean
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiration.before(new Date());
    }
}