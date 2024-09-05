package com.example.practicaClase.persintence.DTOs.User;


public class UserForLogin {
    private String name;
    private String password;

    // Constructor vacío
    public UserForLogin() {}

    // Constructor con argumentos
    public UserForLogin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Getters y Setters
    public String getUsername() {
        return name;
    }

    public void setUsername(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
