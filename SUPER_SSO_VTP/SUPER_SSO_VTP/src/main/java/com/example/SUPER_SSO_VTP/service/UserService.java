package com.example.SUPER_SSO_VTP.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    Map<String, Object> getUserInfo(String username, String maBuuCuc) throws Exception;

    void sendOtpViaSms(String phone) throws Exception;

    Long getUserTruongBC(String maBC) throws Exception;
}
