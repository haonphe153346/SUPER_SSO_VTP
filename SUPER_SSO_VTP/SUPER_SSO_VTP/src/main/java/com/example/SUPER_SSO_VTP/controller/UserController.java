package com.example.SUPER_SSO_VTP.controller;

import com.example.SUPER_SSO_VTP.service.UserService;
import com.example.SUPER_SSO_VTP.service.impl.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserImpl userService;

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

    @GetMapping("/api")
    public String test() {
        return "lol api";
    }

    //Test Send Redirect
//    @GetMapping(value = {"/","/home"})
//    public void tes(HttpServletResponse response) throws IOException {
//        response.sendRedirect("https://www.google.com"+"?app_id=abc");
//    }

    @GetMapping(value = {"/test", "/testAPI"})
    public UserDetailsService testAPI() {
        Map<String, String> maps = new HashMap<>();
        maps.put("a", "a");
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withDefaultPasswordEncoder() // Sử dụng mã hóa password đơn giản
                        .username("test")
                        .password("test")
                        .roles("USER") // phân quyền là người dùng.
                        .build()
        );
        return manager;
    }

    @GetMapping(value = {"/", "/home"})
    public String homepage() {
        return "home"; // Trả về home.html
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello"; // Trả về hello.html
    }
}
