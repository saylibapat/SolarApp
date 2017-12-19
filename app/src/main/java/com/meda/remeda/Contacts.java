package com.meda.remeda;

/**
 * Created by swarada on 7/27/2017.
 */

public class Contacts {
    //int id;
    String name,email,uname,pass;
    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getUsername(){
        return this.uname;
    }
    public String getPassword(){
        return this.pass;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setUsername(String uname){
        this.uname = uname;
    }    public void setPassword(String pass){
        this.pass = pass;
    }
}
