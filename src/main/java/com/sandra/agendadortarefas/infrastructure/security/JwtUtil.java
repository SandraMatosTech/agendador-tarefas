package com.sandra.agendadortarefas.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtil {

    // A chave secreta deve ter pelo menos 32 caracteres
    private final String secret = "minha-chave-secreta-muito-segura-com-mais-de-32-caracteres";
    private final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    // Extrai o email (subject) do token
    public String extrairEmailToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extrai a data de expiração
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Método genérico para extrair informações específicas
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extrai todas as informações do corpo do token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Verifica se o token expirou
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Valida o token comparando com o email do usuário
    public Boolean validateToken(String token, String email) {
        final String userEmail = extrairEmailToken(token);
        return (userEmail.equals(email) && !isTokenExpired(token));
    }
}