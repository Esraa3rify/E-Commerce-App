package com.example.model;

public class User {

        private String name, Phone, passWord;
        public User(){


        }

        public User(String name,String Phone,String passWord) {
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

