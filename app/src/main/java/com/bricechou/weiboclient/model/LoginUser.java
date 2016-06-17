package com.bricechou.weiboclient.model;

/**
 * This is a Model for current login user.
 * To record user related information.
 *
 * @author BriceChou
 * @datetime 16-6-17 11:31
 * @TODO
 */
public class LoginUser {
    private Integer id;
    private String username;
    private String passwrod;

    public LoginUser(Integer id, String username, String passwrod) {
        this.id = id;
        this.passwrod = passwrod;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPasswrod() {
        return passwrod;
    }

    public void setPasswrod(String passwrod) {
        this.passwrod = passwrod;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
