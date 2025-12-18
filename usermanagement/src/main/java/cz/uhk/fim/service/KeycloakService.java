package cz.uhk.fim.service;

import cz.uhk.fim.entity.StudentEntity;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final UsersResource keycloakUsersResource;

    private static final String DEFAULT_USER_ROLE = "USER";
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
            user.setRealmRoles(new ArrayList<>(List.of(DEFAULT_USER_ROLE, defaultRole)));

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
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create user");
            }
        } catch (Exception e) {
            log.error("Error during user registration", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }
    }

    public void updateKeycloakUserAttributes(StudentEntity studentEntity, String email, String firstName, String lastName) {
        try {
            UserRepresentation user = keycloakUsersResource.get(studentEntity.getKeycloakId().toString()).toRepresentation();

            boolean isUpdated = false;

            if (email != null && !email.equals(user.getEmail())) {
                user.setEmail(email);
                isUpdated = true;
            }
            if (firstName != null && !firstName.equals(user.getFirstName())) {
                user.setFirstName(firstName);
                isUpdated = true;
            }
            if (lastName != null && !lastName.equals(user.getLastName())) {
                user.setLastName(lastName);
                isUpdated = true;
            }

            if (isUpdated) {
                keycloakUsersResource.get(studentEntity.getKeycloakId().toString()).update(user);
                log.info("User attributes updated successfully for userId: {}", studentEntity.getKeycloakId());
            } else {
                log.info("No changes detected for userId: {}", studentEntity.getKeycloakId());
            }


        } catch (Exception e) {
            log.error("Error updating user attributes for userId: {}", studentEntity.getKeycloakId(), e);
        }

    }

    public void deleteUser(UUID keycloakId) {
        keycloakUsersResource.get(keycloakId.toString()).remove();
        log.info("User deleted successfully: {}", keycloakId);
    }

    public List<String> getUserRolesByKeycloakId(String keycloakId) {
        return keycloakUsersResource.get(keycloakId).roles().realmLevel()
                .listEffective()
                .stream()
                .filter(role -> role.getName() != null)
                .filter(role -> role.getName().toUpperCase().equals(role.getName()))
                .map(RoleRepresentation::getName)
                .toList();
    }
//
//    public boolean isReadyToAnonymize(UUID keycloakId) {
//        try {
//            UserRepresentation user = keycloakUsersResource.get(keycloakId.toString()).toRepresentation();
//            var attribute = user.getAttributes().get("lastLogin");
//            log.info("User attribute lastLogin: {}", attribute);
//            return user != null;
//        } catch (Exception e) {
//            log.error("Error checking user for anonymization: {}", keycloakId, e);
//            return false;
//        }
//
//    }
}
