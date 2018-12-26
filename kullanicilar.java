package com.example.hp.myapplication;

/**
 * Created by Hp on 28.12.2017.
 */

public class kullanicilar {
    private String kullaniciId;
    private String kullaniciName;
    private String kullaniciSurname;
    private String odano;
    private String bölüm;


    public kullanicilar(){

    }


    public String getKullaniciId() {
        return kullaniciId;
    }

    public void setkullaniciId(String kullaniciId) {
        this.kullaniciId = kullaniciId;
    }
    public String getkullaniciName() {
        return kullaniciName;
    }


    public void setkullaniciName(String kullaniciName) {
        this.kullaniciName = kullaniciName;
    }


    public String getkullaniciSurname() {
        return kullaniciSurname;
    }

    public void setKullaniciSurname(String kullaniciSurname) {
        this.kullaniciSurname = kullaniciSurname;
    }

    public String getodano() {
        return odano;
    }
    public void setOdano(String odano) {
        this.odano = odano;
    }

    public String getBölüm() {
        return bölüm;
    }
    public void setBölüm(String bölüm) {
        this.bölüm = bölüm;
    }



    public kullanicilar(String kullaniciId, String kullaniciName, String kullaniciSurname){

        this.kullaniciId = kullaniciId;
        this.kullaniciName =kullaniciName;
        this.kullaniciSurname = kullaniciSurname;
        this.odano=odano;
        this.bölüm=bölüm;
    }
}
