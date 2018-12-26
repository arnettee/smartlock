package com.example.hp.myapplication;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tani extends AppCompatActivity {
    private TextView d;
    private Button ip, admin;
    String b;
    String ay;
    LinearLayout ln;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference oku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tani);

        d = (TextView) findViewById(R.id.ipal);
        ip = (Button) findViewById(R.id.ip);
        ln=(LinearLayout) findViewById(R.id.LinearLayout2);


        ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mac c = new mac();
                b=c.getMacadres().toString();
                oku=FirebaseDatabase.getInstance().getReference().child("kullanicilar");
                ValueEventListener yaz = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {

                            kullanici k = new kullanici();
                            k = dataSnapshot1.getValue(kullanici.class);
                            String a=k.isim.toString();
                            String c=k.soyisim.toString();
                            String m = k.mac.toString();
                            kontrol(m,a);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                };
                oku.addValueEventListener(yaz);
            }
        });

        admin = (Button) findViewById(R.id.admin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(Tani.this, admin_giris.class);
                startActivity(inte);
            }
        });
    }
    public void kontrol(String di, String ce){
        if(b.equals(di)){
            ay=ce;
            Intent inte = new Intent(Tani.this, AnaActivity.class);
            inte.putExtra("anahtar",ay);
            startActivity(inte);
        }
    }
}
