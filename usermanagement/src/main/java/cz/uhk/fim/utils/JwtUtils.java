package cz.uhk.fim.utils;

import org.springframework.security.oauth2.jwt.Jwt;

public class JwtUtils {


    public static String getKeycloakIdFromJwt(Jwt jwt) {
        return jwt.getClaims().get("sub").toString();
    }
}
