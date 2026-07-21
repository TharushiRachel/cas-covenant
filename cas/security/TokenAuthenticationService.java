package com.itechro.cas.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechro.cas.model.dto.master.UserDTO;
import com.itechro.cas.model.dto.master.UserLoginResponse;
import com.itechro.cas.model.security.LoginCredentials;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class TokenAuthenticationService {

    private static final String SECRET = "A1b2C3d4E5f6G7h8I9j0K1l2M3n4O5p6Q7r8S9t0U1v2W3x4Y5z6a7B8c9D01234";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";
    private static final String HEADER_STRING_REFRESH = "Refresh";
    private static final Logger LOG = LoggerFactory.getLogger(TokenAuthenticationService.class);
    private static long ACCESS_TOKEN_EXPIRATION_TIME;
    private static long REFRESH_TOKEN_EXPIRATION_TIME;
    private static String JWT_TOKEN_ISSUER;
    private static ObjectMapper objectMapper = new ObjectMapper();


    public TokenAuthenticationService(@Value("${token.expiration.duration.in.minutes}") String tokenExpirationDuration,
                                      @Value("${refresh.token.expiration.duration.in.minutes}") String refreshTokenExpirationDuration,
                                      @Value("${jwt.token.issuer}") String tokenIssuer) {
        try {
            ACCESS_TOKEN_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(Long.parseLong(tokenExpirationDuration));
            REFRESH_TOKEN_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(Long.parseLong(refreshTokenExpirationDuration));
            JWT_TOKEN_ISSUER = tokenIssuer;
        } catch (NumberFormatException e) {
            ACCESS_TOKEN_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(15);
            REFRESH_TOKEN_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(120);
            JWT_TOKEN_ISSUER = "http://itechro.com";
        }
    }

    static void addAuthentication(HttpServletResponse res, UserDTO userDTO) throws IOException {

        String accessToken = generateAccessToken(userDTO);
        String refreshToken = generateRefreshToken(userDTO);

        res.setContentType(MediaType.APPLICATION_JSON.toString());
        userDTO.setPassword(null);

        UserLoginResponse loginResponse = new UserLoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setUser(userDTO);

        res.getWriter().write(objectMapper.writeValueAsString(loginResponse));
    }

    public static String generateAccessToken(UserDTO userDTO) {
        return generateJwtToken(userDTO, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public static String generateRefreshToken(UserDTO userDTO) {
        return generateJwtToken(userDTO, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public static String generateAccessToken(String userName) {
        return generateJwtToken(userName, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public static String generateRefreshToken(String userName) {
        return generateJwtToken(userName, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private static String generateJwtToken(UserDTO userDTO, long expirationTimeMs) {

        Map<String, Object> claims = new HashMap();
        claims.put(Claims.SUBJECT, getUserName(userDTO));

        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuer(JWT_TOKEN_ISSUER)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMs))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    private static String generateJwtToken(String userName, long expirationTimeMs) {
        Map<String, Object> claims = new HashMap();
        claims.put(Claims.SUBJECT, userName);

        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuer(JWT_TOKEN_ISSUER)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMs))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    public static LoginCredentials authenticateAndGetUsername(HttpServletRequest request) throws IllegalAccessException {
        String token = request.getHeader(HEADER_STRING);

        if (token == null) {
//			LOG.warn("Token not recieved in request originating from : {}", request.getRemoteHost());
            return null;
        }

        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();

        LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setUsername(claims.getSubject());

        return loginCredentials;
    }

    private static String getUserName(UserDTO userDTO) {
        return userDTO.getUserName();
    }
}
