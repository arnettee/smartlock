package com.example.hp.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class kullanici_ekle extends AppCompatActivity {
    private TextView isim, soyisim, akt, blm, oda, mac, blm_id;
    private EditText ad, soyad, ak, bl, od, mc, bl_id;
    private Button ekle;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference oku, oku1, oku2, oku3, oku4;
    int sayac =20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_ekle);

        isim=(TextView) findViewById(R.id.isim);
        ad = (EditText) findViewById(R.id.ad);
        soyisim = (TextView) findViewById(R.id.soyisim);
        soyad = (EditText) findViewById(R.id.soyad);
        akt=(TextView) findViewById(R.id.akt);
        ak = (EditText) findViewById(R.id.ak);
        blm =(TextView) findViewById(R.id.blm);
        bl = (EditText) findViewById(R.id.bl);
        oda =(TextView) findViewById(R.id.oda);
        od = (EditText) findViewById(R.id.od);
        mac =(TextView) findViewById(R.id.mac);
        mc = (EditText) findViewById(R.id.mc);
        blm_id =(TextView) findViewById(R.id.blm_id);
        bl_id = (EditText) findViewById(R.id.bl_id);
        ekle = (Button) findViewById(R.id.ekle);

        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sayac=sayac +1;
                oku=FirebaseDatabase.getInstance().getReference().child("kullanicilar").child("kullanici"+sayac);
                        ValueEventListener yaz = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Map<String, Object> hopperUpdates = new HashMap<>();
                                hopperUpdates.put("isim", ad.getText().toString());
                                hopperUpdates.put("soyisim", soyad.getText().toString());
                                hopperUpdates.put("aktiflik_durumu", ak.getText().toString());
                                hopperUpdates.put("mac", mc.getText().toString());
                                hopperUpdates.put("bolum", bl.getText().toString());
                                hopperUpdates.put("oda_id", od.getText().toString());
                                oku.updateChildren(hopperUpdates);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        };
                        oku.addValueEventListener(yaz);
                    }

        });
    }
}
