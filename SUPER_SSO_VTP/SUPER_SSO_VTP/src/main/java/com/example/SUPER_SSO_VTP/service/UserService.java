package com.example.SUPER_SSO_VTP.service;

import com.example.SUPER_SSO_VTP.exception.VtException;
import com.example.SUPER_SSO_VTP.model.response.UserData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    Map<String, Object> getUserInfo(String username, String maBuuCuc) throws Exception;

    void sendOtpViaSms(String phone) throws Exception;

    Long getUserTruongBC(String maBC) throws Exception;

    Map<String, Object> getUserData(String username, String maBuuCuc) throws Exception;

    Map<String, Map<String, Object>>  TestData() throws Exception;

    Object[] test(int rowStart, int rowEnd) throws Exception;
}
