package com.example.miolas2projettp;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String email;
    private String password;
    private String full_name;
    public enum RoleEnum { STUDENT , PROFESSOR , ADMIN };
    private String role;
    private String departemnt;
    private String additional_info;
    private int photo;

    public User(String email, String full_name, String departemnt, String additional_info, int photo) {
        this.email = email;
        this.full_name = full_name;
        this.departemnt = departemnt;
        this.additional_info = additional_info;
        this.photo = photo;
    }

    public User(String email, String full_name, String role) {
        this.email = email;
        this.full_name = full_name;
        this.role = role;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public String getDepartemnt() {
        return departemnt;
    }
    public void setDepartemnt(String departemnt) {
        this.departemnt = departemnt;
    }

    public String getAdditional_info() {
        return additional_info;
    }
    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public int getPhoto() {
        return photo;
    }
    public void setPhoto(int photo) {
        this.photo = photo;
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



//    public User(String email, String password, String full_name) {
//        this.email = email;
//        this.password = password;
//        this.full_name = full_name;
//    }