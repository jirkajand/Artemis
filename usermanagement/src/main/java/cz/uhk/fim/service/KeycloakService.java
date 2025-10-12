package cz.uhk.fim.service;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final UsersResource keycloakUsersResource;

    public Optional<String> registerUser(RegisterKeycloakUserDTO request, String defaultRole) {
        try {
            // basic user info
            UserRepresentation user = new UserRepresentation();
            user.setEnabled(true);
            user.setEmail(request.getEmail());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            // NOTE: Setting emailVerified to false creates unverified user accounts.
            // TODO: Implement email verification flow to ensure users verify their email address.
            // If you intentionally allow unverified accounts, document this decision and its security implications.
            user.setEmailVerified(false);

            // password
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(request.getPassword());
            credential.setTemporary(false);
            user.setCredentials(Collections.singletonList(credential));

            // default role
            user.setRealmRoles(Collections.singletonList(defaultRole));

            Response response = keycloakUsersResource.create(user);

            int status = response.getStatus();
            response.close();


            if (status == 201) {
                String location = response.getHeaderString("Location");
                String userId = location != null ? location.substring(location.lastIndexOf('/') + 1) : null;

                log.info("User created successfully: {}, id: {}", request.getEmail(), userId);
                return Optional.of(userId);
            } else {
                log.error("Failed to create user. Status: {}", status);
                return Optional.empty();
            }

        } catch (Exception e) {
            log.error("Error during user registration", e);
            return Optional.empty();
        }
    }
}
