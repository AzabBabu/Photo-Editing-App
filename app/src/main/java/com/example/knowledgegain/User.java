package com.example.knowledgegain;

public class User {

    private String name;
    private String mail;
    private String password;
    private String refer_code;


    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public User() {
    }

    private long coins = 54;


    public User(String name, String mail, String password, String refer_code ) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.refer_code = refer_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefer_code() {
        return refer_code;
    }

    public void setRefer_code(String refer_code) {
        this.refer_code = refer_code;
    }

}
