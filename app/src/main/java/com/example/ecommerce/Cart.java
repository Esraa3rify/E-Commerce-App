package com.example.ecommerce;

public class Cart {

    private static String pid,pname,price,quantity, discount;

    public Cart() {
    }

    public Cart(String pname, String price, String pid,String quantity,String discount) {
        this.pname = pname;
        this.price = price;
        this.pid = pid;
        this.quantity=quantity;
        this.discount=discount;
    }

    public static String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public static String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public static String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
