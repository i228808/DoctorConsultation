package com.proj.Bean;

public class LoginSession {
    int id;
    String role;
    public LoginSession(int id, String password) {
        this.id = id;
        this.role = password;
    }
    private static LoginSession instance = null;
    public int getId() {
        return id;
    }
    public String getRole(){
        return role;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public LoginSession getInstace(){
        if(instance == null){
            instance = new LoginSession(0,"");
        }
        else{
            return instance;
        }
        return instance;
    }

}
