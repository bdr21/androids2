package com.example.miolas2projettp;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String email;
    private String password;
    private String full_name;
    public enum RoleEnum { STUDENT , PROFESSOR , ADMIN };
    private String role;

//    public User(String email, String password, String full_name) {
//        this.email = email;
//        this.password = password;
//        this.full_name = full_name;
//    }
    public User(String email,String full_name, RoleEnum role) {
        this.email = email;
        this.full_name = full_name;
        this.role = RoleEnum.PROFESSOR.toString();
    }
    public Map<String, Object> getInfo() {
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("full_name", full_name);
        user.put("role", role);
        return user;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
