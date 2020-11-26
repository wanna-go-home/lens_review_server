package com.springboot.intelllij.utils;

import com.springboot.intelllij.domain.AccountEntity;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.Instant;
import java.time.Period;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenUtils {

    private static final String secretKey = "Wanna_go_home_secret";

    public String generateJwt(AccountEntity account) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(account.getAccount())
                .setHeader(createHeader())
                .setClaims(createClaims(account))
                .setExpiration(createExpireDateForOneYear())
                .signWith(SignatureAlgorithm.HS256,createSigningKey());
        return builder.compact();
    }

    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException e) {
            return false;
        } catch (NullPointerException e) {
            return  false;
        }
    }

    public static String getTokenFromHeader(String header) {
            return header.split(" ")[1];
    }

    public static String getUserAccount(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("id").toString();
    }

    private static Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("type","JWT");
        header.put("alg","HS256");
        header.put("regDate",System.currentTimeMillis());
        return header;
    }

    private Map<String, Object> createClaims(AccountEntity account) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",account.getAccount());
        claims.put("nickname",account.getNickname());
        return claims;
    }

    private Date createExpireDateForOneYear() {
        Instant date = Instant.now();
        return Date.from(date.plus(Period.ofDays(365)));
    }

    private Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
