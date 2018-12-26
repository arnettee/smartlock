package com.example.hp.myapplication;

import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.logging.SocketHandler;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity implements OnItemClickListener{

    private Button KilitAc, KilitKapat1 ;
    private TextView text;
    private ListView liste;
    String dila="1";


    public static final UUID myuuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static final int baglanti=0;
    public static final int mesajoku=1;

    OutputStream outstream ;
    InputStream instream ;
    ArrayAdapter<String> adaptorlist;
    private BluetoothAdapter blt=null;
    ArrayList<String> eslesen;
    IntentFilter filtre;
    private BroadcastReceiver reciver;
    Set<BluetoothDevice> devicearray;  //eslesmis cihazlar
    ArrayList<BluetoothDevice> aygitlar = new  ArrayList(20);
    BluetoothAdapter bondedDevices = BluetoothAdapter.getDefaultAdapter();
    Intent newInt = getIntent();
    //String address= newInt.getStringExtra(eslesen.EXTRA_ADDRESS);
    public static String EXTRA_ADDRESS = "device_address";
    private boolean isBtConnected = false;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    String isim;





    Handler mHandler = new Handler(){
        public void handleMessage (Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case baglanti :
                    ConnectedThread connectedThread = new ConnectedThread((BluetoothSocket)msg.obj);
                    text.setText("bağlı");
                    break;
                case mesajoku :
                    byte[] bufoku = (byte[])msg.obj;
                    String str = new String(bufoku);
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent inte = getIntent();

        liste=(ListView) findViewById(R.id.list);
        blt=BluetoothAdapter.getDefaultAdapter();
        eslesen = new ArrayList();
        Bundle gelenVeri=getIntent().getExtras();
        isim= gelenVeri.getCharSequence("anahtar").toString();

        blt=BluetoothAdapter.getDefaultAdapter();   //cihazda bluetooth var mı yok mu?

        filtre = new IntentFilter(BluetoothDevice.ACTION_FOUND); //yalnızca eskiden eşleşen ve şu an yakında olan bluetoothlar

        reciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();


                if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothAdapter bondedDevices = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice aygit = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                   // aygitlar.add(aygit);
                    int x=0;

                    String s = "";

                    for(int i=0; i<eslesen.size(); i++)
                    {
                        if(aygit.getName().equals(eslesen.get(i))){
                            s="(Eşleşti)";

                                 adaptorlist.add(aygit.getName() + "" + s + "");

                                 //eslesen.add(aygit.getName()+""+s+"");
                                 aygitlar.add(aygit);

                                 break;


                        }

                    }
                    //adaptorlist.add(aygit.getName()+""+s+""+"\n"+aygit.getAddress()); //ekstra blt varsa listeye ekledi

                }
                else if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
                    if(blt.getState()==blt.STATE_OFF){
                        bltac();
                    }
                }
            }
        };
        registerReceiver(reciver,filtre);
        filtre = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(reciver,filtre);

        if(blt==null){  //blt null ise ekrana uyarı gelsin
            Toast.makeText(getApplicationContext(), "Cihazda Bluetooth yok", Toast.LENGTH_LONG).show();
        }
        else {
            if (!blt.isEnabled()) {
                bltac();
            }
        }
            secim();
            startDiscovery();   //bluetooth aygıtı aranacak

            KilitAc = (Button) findViewById (R.id.KilitAc);
            KilitAc.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    try{
                        KilidiAc();
                    }
                    catch (IOException e){

                    }
                }
            });
            KilitKapat1 = (Button) findViewById(R.id.KilitKapa);
            KilitKapat1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                        KilidiKapat();
                    }
                    catch (IOException e){

                    }
                }
            });



    }
    private void bltac(){  //bluetooth açık değilse açılması için istekte bulunuyor
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);  //istekte bulunduk
        startActivityForResult(intent,1);   //daha sonra bu aktiviteyi başlattık
    }

    private void secim(){  //aygıtlardan secim yapıcaz

        devicearray = blt.getBondedDevices(); //daha önceden eşleştirilen aygıtlar devicearray e atandı
        if(devicearray.size()>0){
            for(BluetoothDevice aygit:devicearray){
                eslesen.add(aygit.getName());
                aygitlar.add(aygit);
            }
        }
        adaptorlist=new ArrayAdapter(this,android.R.layout.simple_list_item_1,eslesen);
        liste.setAdapter(adaptorlist);
        liste.setOnItemClickListener(this);
    }
    private void startDiscovery(){  //farklı bluetooth aygıtı var mı yok mu arayacak
        blt.cancelDiscovery();  //eğer halihazırda bir arama işlemi varsa önce onu kapatıyoruz
        blt.startDiscovery(); //şimdi tekrar açıyoruz
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_CANCELED){ //çalışırken bir anda bluetooth giderse mesaj verecek
            Toast.makeText(getApplicationContext(), "Bluetooth aygıtını açınız" , Toast.LENGTH_LONG).show();
            finish();
        }
    }


    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3 ){ //üzerine tıklanan aygıtın seçilmesi sağlanacak

        text=(TextView) findViewById(R.id.liste);
        if(blt.isDiscovering()){
            blt.cancelDiscovery(); //aramayı durdur çünkü seçtiğimizle işlem yapıcaz

        }

        if(adaptorlist.getItem(arg2).contains("Eşleşti")){

            BluetoothDevice secilenaygit = aygitlar.get(arg2);
            ConnectThread connect = new ConnectThread(secilenaygit);
            connect.start();

            text.setText("Bağlandı");
        }
        else{
            Toast.makeText(getApplicationContext(), "Bluetooth aygıtına bağlanılamadı" , Toast.LENGTH_LONG).show();

            text.setText("Bağlanılamadı");
        }

    }


    private class ConnectThread extends Thread {
        private boolean ConnectSuccess = true;
        //public BluetoothSocket mmSocket;
        //public  BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice aygit){



            mmDevice = aygit;

            try{
                 mmSocket = aygit.createRfcommSocketToServiceRecord(myuuid);
                //mmSocket.connect();
                //BluetoothDevice cihaz = blt.getRemoteDevice(aygit.getAddress());
                //mmSocket = cihaz.createInsecureRfcommSocketToServiceRecord(myuuid);
                //BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                //mmSocket.connect();
               // mHandler.obtainMessage(baglanti, mmSocket).sendToTarget();

            }
            catch (IOException e){
                text.setText("Bağlantı yok");
            }


        }
      public void run() {
          blt.cancelDiscovery();

           try {
                mmSocket.connect();


           } catch (IOException connectException) {
                try {
                  mmSocket.close();
                } catch (IOException closeException) {

               }
                return;
           }
         //  mHandler.obtainMessage(baglanti, mmSocket).sendToTarget();
        }



        public void cancel(){
           try{
                mmSocket.close();
            }
            catch (IOException e){

            }
        }
    }
    private class ConnectedThread extends Thread {
        //public BluetoothSocket mmSocket;


        public ConnectedThread(BluetoothSocket socket) {

            mmSocket = socket;


            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();

            } catch (IOException e) {

            }

            instream = tmpIn;
            outstream = tmpOut;

        }

        public void run() {
            byte[] buffer;
            int bytes;

            while (true) {
                try {
                    buffer = new byte[1024];
                    bytes = instream.read(buffer);

                    mHandler.obtainMessage(mesajoku, bytes, 0, buffer).sendToTarget();
                } catch (IOException e) {
                    break;
                }

            }

        }

        public void cancel() {

            try {
                mmSocket.close();
            } catch (IOException e) {

            }
        }
    }

    public void KilidiAc() throws IOException{

       outstream = mmSocket.getOutputStream();

        outstream.write("1".getBytes());

}
    public void KilidiKapat() throws IOException{
        outstream = mmSocket.getOutputStream();
        outstream.write("2".getBytes());
    }

}


