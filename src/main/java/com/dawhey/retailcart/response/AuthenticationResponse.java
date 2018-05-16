package com.dawhey.retailcart.response;

public class AuthenticationResponse extends StatusResponse{

    private String token;

    public AuthenticationResponse(String status, String token) {
        super(status);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
