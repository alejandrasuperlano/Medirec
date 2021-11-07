package com.medirec.medirec.Security.JWT;

import com.medirec.medirec.Security.Model.UserSessionDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("scRetJWt5263")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    public String tokenGenerator(Authentication auth){
        UserSessionDetails userSessionDetails = (UserSessionDetails) auth.getPrincipal();
        long expDate= new Date().getTime() + expiration;
        return Jwts.builder().setSubject(userSessionDetails.getUsername()).setExpiration(new Date(expDate)).
                signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean tokenValidation(String token){
        try{
            // validar la firma del token
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e){
            System.err.print("Token mal estructurado");
        } catch (UnsupportedJwtException e){
            System.err.print("El token no es de un tipo soportado");
        } catch (ExpiredJwtException e){
            System.err.print("El token esta vencido");
        } catch (IllegalArgumentException e){
            System.err.print("Token vacío");
        } catch (SignatureException e){
            System.err.print("Firma no válida");
        }
        return false;
    }
}
