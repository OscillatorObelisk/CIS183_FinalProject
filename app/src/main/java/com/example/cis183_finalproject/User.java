package com.example.cis183_finalproject;

public class User
{
    private int id;
    private String uname;
    private String password;
    private String email;

    public User()
    {

    }
    public User(String u, String p, String e)
    {
        uname = u;
        password = p;
        email = e;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
