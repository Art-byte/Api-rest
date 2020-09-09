package com.artcom.Apirest.security.jwt;


//Esta clase genera el Token y lo valida en su estructura

import com.artcom.Apirest.security.entity.UserMain;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private final static Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;


    public String generateToken(Authentication authentication){
        UserMain userMain = (UserMain) authentication.getPrincipal();
        return Jwts.builder().setSubject(userMain.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.ES512, secret)
                .compact();
    }

    public String getUserNameFromToken(String token){
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token){
        try{

            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;

        }catch (MalformedJwtException e){
            LOGGER.error("Token mal formado");
        }catch (UnsupportedJwtException e){
            LOGGER.error("Token no soportado");
        }
        catch (ExpiredJwtException e){
            LOGGER.error("Token expirado");
        }
        catch (IllegalArgumentException e){
            LOGGER.error("Token vacio");
        }
        catch (SignatureException e){
            LOGGER.error("Problema en la firma");
        }
        return false;
    }
}
