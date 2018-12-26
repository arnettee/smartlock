package com.example.hp.myapplication;

public class admin_bilgi {
    public String admin_id;
    public String sifre;


    public admin_bilgi(){}
    public admin_bilgi(String admin_id, String sifre) {
        this.admin_id = admin_id;
        this.sifre = sifre;

    }

    public String getMail(){
        return admin_id;
    }
    public void setMail(String mail){
        this.admin_id = admin_id;
    }
    public String getSifre(){
        return sifre;
    }
    public void setSifre(String sifre){
        this.sifre = sifre;
    }


}

