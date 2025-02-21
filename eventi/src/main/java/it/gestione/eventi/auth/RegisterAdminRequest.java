package it.gestione.eventi.auth;

import lombok.Data;

@Data
public class RegisterAdminRequest {
    private String username;
    private String password;
    private Role role;
}
