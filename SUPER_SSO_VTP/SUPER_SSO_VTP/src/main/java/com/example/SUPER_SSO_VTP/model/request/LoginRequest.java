package com.example.SUPER_SSO_VTP.model.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
    private String postcode;
}
