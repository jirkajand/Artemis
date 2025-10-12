package cz.uhk.fim.service;

import lombok.Data;

@Data
public class RegisterKeycloakUserDTO {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
