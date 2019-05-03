package com.rosid.androidasynctask;

public class usserAsynctask {

    private String id;
    private String name;
    private String username;
    private String email;
    private String addr;
    private String ge;

    public usserAsynctask(String id,String name, String username,String email, String addr, String ge){
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.addr = addr;
        this.ge = ge;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getNama(){
        return name;
    }

    public void setNama(String nama){
        this.name = nama;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getAddr(){
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getGe(){
        return ge;
    }

    public void setGe(String ge){
        this.ge = ge;
    }
}
