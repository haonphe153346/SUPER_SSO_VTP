package com.example.SUPER_SSO_VTP.service.impl;

import com.example.SUPER_SSO_VTP.exception.VtException;
import com.example.SUPER_SSO_VTP.model.response.UserData;
import com.example.SUPER_SSO_VTP.service.UserService;
import com.example.SUPER_SSO_VTP.service.daos.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        userDAO.sendOtpViaSms(phone);
    }


    @Override
    public Long getUserTruongBC(String maBC) throws Exception {
        return null;
    }

    @Override
    public  Map<String, Object> getUserData(String username, String maBuuCuc) throws Exception {
        return userDAO.getUserInfo(username,maBuuCuc);
    }

    @Override
    public Map<String, Map<String, Object>> TestData() throws Exception {
        return userDAO.test();
    }

    @Override
    public Object[] test(int rowStart, int rowEnd) throws Exception {
        return userDAO.test123(rowStart,rowEnd);
    }


}
