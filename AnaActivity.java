package com.example.hp.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AnaActivity extends AppCompatActivity {

    private Button kilit, yetki, kapi;
    private TextView t;
    String veri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana);

        final DatabaseReference databaseReferencekullanicilar = FirebaseDatabase.getInstance().getReference("kullanicilar");
        DatabaseReference db = databaseReferencekullanicilar.push();

        t=(TextView) findViewById(R.id.ipal);
        kilit = (Button) findViewById (R.id.KilitAc);
        yetki = (Button) findViewById (R.id.Yetkilendir);
        kapi = (Button) findViewById (R.id.Yetkili);

        Bundle gelenVeri=getIntent().getExtras();
        t.setText(gelenVeri.getCharSequence("anahtar").toString());
        veri = gelenVeri.getCharSequence("anahtar").toString();

         kilit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent inte = new Intent(AnaActivity.this, MainActivity.class);
                 inte.putExtra("anahtar",veri);
                 startActivity(inte);
             }
         });

        yetki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(AnaActivity.this, asil_yetkililer.class);
                startActivity(inten);
            }
        });

        kapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnaActivity.this, Kapilar.class);
                intent.putExtra("anahtar",veri);
                startActivity(intent);
            }
        });

    }

}
