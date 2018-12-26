package com.example.hp.myapplication;

public class kullanici {
    public String isim;
    public String soyisim;
    public String mac;
    public String aktiflik_durumu;
    public String bolum;
    public String oda_id;
    public String bluetooth;

    public kullanici(){}
    public kullanici(String isim, String soyisim, String mac, String aktiflik_durumu, String bolum, String oda_id, String bluetooth) {
        this.isim = isim;
        this.soyisim = soyisim;
        this.mac = mac;
        this.aktiflik_durumu = aktiflik_durumu;
        this.bolum = bolum;
        this.oda_id = oda_id;
        this.bluetooth = bluetooth;
    }

   public String getIsim(){
       return isim;
    }
  public void setIsım(String isim){
      this.isim = isim;
    }
    public String getSoyisim(){
       return soyisim;
        }
    public void setSoyisim(String soyisim){
        this.soyisim = soyisim;
    }
    public String getmac(){
        return mac;
    }
    public void setmac(String mac){
        this.mac = mac;
    }
    public String getAktiflik(){
        return aktiflik_durumu;
    }
    public void setAktiflik(String aktiflik){
        this.aktiflik_durumu = aktiflik_durumu;
    }
    public String getOda(){
        return oda_id;
    }
    public void setOda(String oda){
        this.oda_id = oda_id;
    }
    public String getBolum(){
        return bolum;
    }
    public void setBolum(String bolum){
        this.bolum = bolum;
    }
    public String getBluetooth(){
        return bluetooth;
    }
    public void setBluetooth(String bluetooth){
        this.bluetooth = bluetooth;
    }
}


