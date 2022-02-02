package com.example.model;

//in this class we will get the phone number and pass and name

public class user {
    private String name, Phone, passWord;


    public user(){


    }

    public user(String name,String Phone,String passWord) {
        this.name = name;
        this.Phone = Phone;
        this.passWord = passWord;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {

        return Phone;

    }

    public void setPhoneNum(String phoneNum) {
        this.Phone = phoneNum;
    }

    public String getPass() {
        return passWord;
    }

    public void setPass(String pass) {
        this. passWord = pass;
    }
}

