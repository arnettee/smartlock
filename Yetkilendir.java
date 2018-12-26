package com.example.hp.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Yetkilendir extends AppCompatActivity {
    private ListView liste;
    private TextView text;
    String a, so, m, akt, bl, od ;
    Adaptor ad;
    int s=0;
    String b;
    kullanici k;
    AlertDialog.Builder ada;


    final List<kullanici> uyeler=new ArrayList<kullanici>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();



    DatabaseReference oku, oku1, oku2;
    //ValueEventListener yaz1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yetkilendir);
        text = (TextView) findViewById(R.id.yetk);
        liste = (ListView) findViewById(R.id.liste);
        mac c = new mac();
        b=c.getMacadres().toString();
        final ListView listemiz = (ListView) findViewById(R.id.liste);
        oku=FirebaseDatabase.getInstance().getReference().child("kullanicilar");
        ValueEventListener yaz = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    k = dataSnapshot1.getValue(kullanici.class);
                    a = k.isim.toString();
                    so= k.soyisim.toString();
                    m= k.mac.toString();
                    akt = k.aktiflik_durumu.toString();
                    bl = k.bolum.toString();
                    od = k.oda_id.toString();

                    uyeler.add(new kullanici(k.isim.toString(),k.soyisim.toString()+" ",k.mac.toString()+" ",k.aktiflik_durumu.toString()+" ",k.bolum.toString()+" ",k.oda_id.toString()+" ", k.bluetooth.toString()));
                    ad=new Adaptor(Yetkilendir.this, uyeler);
                    listemiz.setAdapter(ad);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                text.setText("hata");
            }
        };

            oku.addValueEventListener(yaz);


            listemiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {

                Context context = Yetkilendir.this;
                    String title = "Yetki İzni";
                    String message1 = ad.getItem(i).getIsim().toString() + " " + ad.getItem(i).getSoyisim().toString() + " kişisini yetkilendirmek istiyor musunuz?";
                    String button1String = "EVET";
                    String button2String = "HAYIR";
                    ada = new AlertDialog.Builder(context);
                    ada.setTitle(title);
                    ada.setMessage(message1);
                    ada.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int a) {
                            oku2 = FirebaseDatabase.getInstance().getReference().child("yetkililer").child(b).child("yetkili10");
                            ValueEventListener yaz2 = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Map<String, Object> hopperUpdates = new HashMap<>();
                                    hopperUpdates.put("isim", ad.getItem(i).getIsim().toString());
                                    hopperUpdates.put("soyisim", ad.getItem(i).getSoyisim().toString());


                                    oku2.updateChildren(hopperUpdates);
                                    Intent inte = new Intent(Yetkilendir.this, asil_yetkililer.class);
                                    startActivity(inte);

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            };
                            oku2.addValueEventListener(yaz2);
                        }
                    });
                 ada.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int a) {
                        }
                    });
                ada.setCancelable(true);
                ada.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                    }
                });
                oku1 = FirebaseDatabase.getInstance().getReference().child("yetkililer").child(b);
                ValueEventListener yaz1 = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                            yetkililer y = new yetkililer();
                            y = dataSnapshot1.getValue(yetkililer.class);
                            String a = y.isim.toString();
                            if(a.equals(ad.getItem(i).getIsim().toString())){
                                Intent inte = new Intent(Yetkilendir.this, asil_yetkililer.class);
                                startActivity(inte);
                                Toast.makeText(getApplicationContext(), "SEÇTİĞİNİZ KİŞİ ZATEN YETKİLİ !" , Toast.LENGTH_LONG).show();
                                break;
                            }
                            else{
                                ada.show();
                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                oku1.addValueEventListener(yaz1);


                }


        });


    }

}

