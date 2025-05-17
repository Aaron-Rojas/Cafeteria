package com.cafeteria.demo.model;

public class User { 
    private String email;
    private String username;
    private String password;
    private String nombres;

    public User() {

    } 
    


    public User(String email, String nombres, String password) {
        this.email = email;
        this.username = username;
        this.password = password;

    }

    // getters y setters
    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }

    public String getUsername() { 
        return username; 
    }
    public void setUsername(String username) { 
        this.username = username; 
    }

    public String getNombres(){
        return nombres; 
    }

    public void setNombres(String nombres){
        this.nombres=nombres; 
    }

    @Override
    public String toString() {
        return "User{" +
               "email='" + email + '\'' +
               ", username='" + username + '\'' +
               // No imprimir la contraseña por seguridad, excepto quizás en depuración muy controlada
               // ", password='" + password + '\'' +
               '}';
    }
}
