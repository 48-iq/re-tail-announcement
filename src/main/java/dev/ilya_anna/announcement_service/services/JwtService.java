package dev.ilya_anna.announcement_service.services;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public interface JwtService {

    DecodedJWT verifyAccessToken(String token) throws JWTVerificationException;
}
