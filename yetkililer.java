package com.example.hp.myapplication;

public class yetkililer {

        public String isim;
        public String soyisim;
        public String mac;

        public yetkililer(){}
        public yetkililer(String isim, String soyisim, String mac) {
            this.isim = isim;
            this.soyisim = soyisim;
            this.mac = mac;
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

    }


