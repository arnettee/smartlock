package com.example.hp.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin extends AppCompatActivity {
private Button kul, oda, yetki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        kul = (Button) findViewById(R.id.kul);
        oda = (Button) findViewById(R.id.oda);
        yetki = (Button) findViewById(R.id.yetki);

        kul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(admin.this, kullanici_islem.class);
                startActivity(in);
            }
        });

        oda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(admin.this, oda_islem .class);
                startActivity(in);
            }
        });


    }
}
