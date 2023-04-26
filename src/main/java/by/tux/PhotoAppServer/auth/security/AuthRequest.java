package by.tux.PhotoAppServer.auth.security;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;

}
