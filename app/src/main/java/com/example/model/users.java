package com.example.model;

public class users {
    private String name, password,phone;
    public users(){

}

    public users(String name,String phone,String password) {
        this.name = name;
        this.password = password;
        this.phone = phone;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return password;
    }

    public void setPass(String password) {
        this. password = password;
    }


    public String getPhoneNum() {

        return phone;

    }

    public void setPhoneNum(String phone) {
        this.phone = phone;
    }



}
