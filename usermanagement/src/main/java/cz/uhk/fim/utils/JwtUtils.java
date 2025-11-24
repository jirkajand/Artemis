package cz.uhk.fim.utils;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public class JwtUtils {


    public static String getKeycloakIdFromJwt(Jwt jwt) {
        return jwt.getClaims().get("sub").toString();
    }

    public static UUID getIdFromSecurityContext(SecurityContext securityContext) {
        Jwt jwt = (Jwt) securityContext.getAuthentication().getPrincipal();
        String idString = getKeycloakIdFromJwt(jwt);
        return UUID.fromString(idString);
    }
}
