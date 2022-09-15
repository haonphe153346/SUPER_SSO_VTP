package com.example.SUPER_SSO_VTP.service.impl;

import com.example.SUPER_SSO_VTP.service.UserService;
import com.example.SUPER_SSO_VTP.service.daos.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class UserImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public Map<String, Object> getUserInfo(String username, String maBuuCuc) throws Exception {
        return userDAO.getUserInfo(username,maBuuCuc);
    }

    @Override
    public void sendOtpViaSms(String phone) throws Exception {

    }

    @Override
    public Long getUserTruongBC(String maBC) throws Exception {
        return null;
    }
}
