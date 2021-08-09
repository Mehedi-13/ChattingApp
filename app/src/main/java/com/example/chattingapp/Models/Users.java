package com.example.chattingapp.Models;

public class Users {

    String profile, email, phone, password, name,userId,lastMessage;




    public Users(String profile, String email, String phone, String password, String name, String userId, String lastMessage) {
        this.profile = profile;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Users() {
    }

    public Users(String email, String phone, String password, String name) {
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.name = name;
    }





    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
