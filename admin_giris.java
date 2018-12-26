package com.example.hp.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_giris extends AppCompatActivity {
private Button giris;
    private EditText editText, editText1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference oku, oku1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_giris);
        editText = (EditText) findViewById(R.id.editText);
        editText1 = (EditText) findViewById(R.id.editText2);

        giris = (Button) findViewById(R.id.ip);
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oku=FirebaseDatabase.getInstance().getReference().child("adminler").child("admin1");
                ValueEventListener yaz = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        admin_bilgi a = new admin_bilgi();
                        a = dataSnapshot.getValue(admin_bilgi.class);
                        if(editText.getText().toString().equals(a.admin_id.toString())){
                            if(editText1.getText().toString().equals(a.sifre.toString())){
                                Toast.makeText(getApplicationContext(), "BAŞARILI GİRİŞ !" , Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(admin_giris.this, admin.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "MAİL VE ŞİFRE UYUŞMUYOR !" , Toast.LENGTH_LONG).show();
                            }
                        }
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
