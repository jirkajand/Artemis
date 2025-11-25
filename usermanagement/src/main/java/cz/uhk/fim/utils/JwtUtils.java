package cz.uhk.fim.utils;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public class JwtUtils {


    public static String getKeycloakIdFromJwt(Jwt jwt) {
        return jwt.getClaims().get("sub").toString();
    }

    public static UUID getIdFromSecurityContext(SecurityContext securityContext) {
        if (securityContext == null) {
            throw new IllegalStateException("SecurityContext is null");
        }
        if (securityContext.getAuthentication() == null) {
            throw new IllegalStateException("Authentication is missing in SecurityContext");
        }
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal == null) {
            throw new IllegalStateException("Principal is missing in Authentication");
        }
        if (!(principal instanceof Jwt jwt)) {
            throw new IllegalStateException("Principal is not an instance of Jwt");
        }
        String idString = getKeycloakIdFromJwt(jwt);
        return UUID.fromString(idString);
    }
}
