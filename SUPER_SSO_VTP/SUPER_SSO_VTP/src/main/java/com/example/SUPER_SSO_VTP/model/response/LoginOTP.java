package com.example.SUPER_SSO_VTP.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginOTP {
    private String phone;
    private String otp;
    private int count_otp;
}
