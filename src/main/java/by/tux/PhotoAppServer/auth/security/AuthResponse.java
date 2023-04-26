package by.tux.PhotoAppServer.auth.security;

import lombok.Data;

@Data
public class AuthResponse {
    private String jwtToken;

    public AuthResponse() {
    }

    public AuthResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

}
