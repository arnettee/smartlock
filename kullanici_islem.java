package com.example.hp.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class kullanici_islem extends AppCompatActivity {
    private ListView liste;
    Adaptor ad;
    final List<kullanici> uyeler=new ArrayList<kullanici>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference oku;
    Button ekle;
    TextView dene;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_islem);
        liste = (ListView) findViewById(R.id.liste);
        final ListView listemiz = (ListView) findViewById(R.id.liste);
        ekle = (Button) findViewById(R.id.ekle);
        dene = (TextView) findViewById(R.id.dene);
        oku=FirebaseDatabase.getInstance().getReference().child("kullanicilar");

        ValueEventListener yaz = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    kullanici k = new kullanici();
                    k = dataSnapshot1.getValue(kullanici.class);
                    uyeler.add(new kullanici(k.isim,k.soyisim+" ",k.mac+" ",k.aktiflik_durumu+" ",k.bolum+" ",k.oda_id +" ", k.bluetooth));

                    ad=new Adaptor(kullanici_islem.this, uyeler);
                    listemiz.setAdapter(ad);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        oku.addValueEventListener(yaz);
        listemiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Intent in = new Intent(kullanici_islem.this, guncelle.class);
                in.putExtra("anahtar",ad.getItem(i).getIsim().toString());
                startActivity(in);
            }
        });

        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(kullanici_islem.this, kullanici_ekle.class);
                startActivity(inte);
            }
        });

    }
}
