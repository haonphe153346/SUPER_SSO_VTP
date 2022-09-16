package com.example.SUPER_SSO_VTP.controller;

import com.example.SUPER_SSO_VTP.base.BaseResponse;
import com.example.SUPER_SSO_VTP.exception.VtException;
import com.example.SUPER_SSO_VTP.model.request.LoginRequest;
import com.example.SUPER_SSO_VTP.service.UserService;
import com.example.SUPER_SSO_VTP.service.impl.UserImpl;
import com.example.SUPER_SSO_VTP.util.Constants;
import com.example.SUPER_SSO_VTP.util.EncryptionUtil;
import com.example.SUPER_SSO_VTP.util.RsaCrypto;
import com.example.SUPER_SSO_VTP.util.Utils;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
//@RequestMapping("/api")
public class UserController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @GetMapping("/abc")
    public Map<String, Object> log() throws Exception {
        return userService.getUserInfo("nguontest22", "TN2");
    }

    @GetMapping("/login")
    public String cmdlogin() {
        //1. Get app_id = ? and redirect_uri = ?

        //2. Check account exist in database

        //3. If(account) exist => generate share code = { app_id = ? ; access_token = ? ; UserObj = ? }

        //4. Get share code from app_id and xac minh share code

        //5. Generate Token + ListRole + ListRoleDetails

        //6. Return Token
        return "testxx";
    }

    @PostMapping("/v2login")
    public ResponseEntity login(@RequestBody LoginRequest request) throws Exception {

            if (Utils.isNullOrEmpty(request.getUsername())) {
                throw new VtException(Constants.STATUS.E203, "Tài khoản không được để trống");
            }
            if (Utils.isNullOrEmpty(request.getPassword())) {
                throw new VtException(Constants.STATUS.E203, "Mật khẩu không được để trống");
            }
            Map<String, Object> result = userService.getUserInfo(request.getUsername(), request.getPostcode());
            String salt = (String) result.get(Constants.pwdSaltKey);
            String pwdEncrypted = (String) result.get(Constants.pwdKey);
            String compare = EncryptionUtil.sha256Encode(request.getPassword(), salt);
//            if (result.isEmpty() || compare == null || !compare.equals(pwdEncrypted)) {
//                throw new VtException(Constants.STATUS.E204, "Tài khoản hoặc mật khẩu không hợp lệ");
//            }
            return new ResponseEntity(new BaseResponse(false, createJWT(result), result), HttpStatus.OK);
    }

    private String createJWT(Map<String, Object> claim) throws Exception {
        Long exp = System.currentTimeMillis() + 24L * 60 * 60 * 1000;
        claim.remove(Constants.pwdSaltKey);
        claim.remove(Constants.pwdKey);
        claim.put("exp", exp + "");
        claim.put("source", -1);
        JsonObject header = new JsonObject();
        header.addProperty("alg", "RS256");
        header.addProperty("typ", "JWT");
        header.addProperty("exp", exp + "");
        RSAPrivateKey _privateKey = RsaCrypto.LoadPrivateKey2("keys/EvtpPrivate.pem", true);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(_privateKey.getEncoded());
        String retStr = null;
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey privKey = kf.generatePrivate(keySpec);
            retStr = Jwts.builder().setClaims(claim).signWith(SignatureAlgorithm.RS256, privKey).compact();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return retStr;
    }

    @GetMapping("/send-sms-otp")
    public ResponseEntity sendSmsOtp(@RequestBody String phone) throws Exception {
            int count = 0;

            userService.sendOtpViaSms(Utils.phoneHr(phone));
            return new ResponseEntity(new BaseResponse(false, "Gửi Mã xác nhận thành công", "msg"), HttpStatus.OK);
    }
    @GetMapping("/otp")
    public ResponseEntity generateCode(){
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?<>)";
        String values = Capital_chars + Small_chars +
                numbers + symbols;
        // Using random method
        Random rndm_method = new Random();
        char[] password = new char[8];
        for (int i = 0; i < 8; i++)
        {
            password[i] = values.charAt(rndm_method.nextInt(values.length()));
        }
        String rs = "";
        for (int i  = 0; i < 8; i++) {
            rs = rs + password[i];
        }
        return new ResponseEntity(new BaseResponse(true, "Gửi Mã xác nhận thành công", rs), HttpStatus.OK);
    }
}



