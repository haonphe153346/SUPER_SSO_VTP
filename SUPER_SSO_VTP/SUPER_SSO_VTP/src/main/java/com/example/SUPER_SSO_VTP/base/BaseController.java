package com.example.SUPER_SSO_VTP.base;


import com.example.SUPER_SSO_VTP.entity.UserEntity;
import com.example.SUPER_SSO_VTP.util.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class BaseController {
    protected UserEntity getCurrentUser() throws Exception {
        UserEntity info = null;
        try {
            info = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
        }
        if (info == null || info.getUserId() == null || !info.verify()) {
            throw new Exception();
        }
        return info;
    }

    protected ResponseEntity<BaseResponse> successApi(Object data, String message) {
        return ResponseEntity.ok(new BaseResponse(false, message, data));
    }

    protected ResponseEntity<BaseResponse> customOutput(Boolean error, Object data, String message) {
        return ResponseEntity.ok(new BaseResponse(error, message, data));
    }

    protected ResponseEntity<BaseResponse> errorApi(String message) {
        return ResponseEntity.ok(new BaseResponse(true, message, null));
    }

    protected ResponseEntity<BaseResponse> errorWithDataApi(Object data, String message) {
        return ResponseEntity.ok(new BaseResponse(true, message, data));
    }

    public ResponseEntity resultOk(String message, Object body) {
        return new ResponseEntity(new BaseResponse(false, Utils.isNullOrEmpty(message) ? "OK" : message, body), HttpStatus.OK);
    }
}
