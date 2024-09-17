package com.example.practicaClase.persintence.DTOs.User;

public class UserForLogin {
    private String mail;
    private String password;

    // Constructor vacío
    public UserForLogin() {}

    // Constructor con argumentos
    public UserForLogin(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    // Getters y Setters
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
}
