package com.wahyuwidian7gmail.pertemuan12_data_dan_akses_file;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    EditText nama, telepon;
    TextView dataTelepon;
    Button tombolInput;

    ListView daftarTelepon;
    ArrayList<String> arrayKontak;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nama = (EditText) findViewById(R.id.editNama);
        telepon = (EditText) findViewById(R.id.editTelepon);
        dataTelepon = (TextView) findViewById(R.id.textDataTelp);
        tombolInput = (Button) findViewById(R.id.buttonInput);

        daftarTelepon = (ListView) findViewById(R.id.listview);
        arrayKontak = new ArrayList<String>();

        tombolInput.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                byte[] bufferNama = new byte[30];
                byte[] bufferTelepon = new byte[15];

                salinData(bufferNama, nama.getText().toString());
                salinData(bufferTelepon, telepon.getText().toString());

                try{

                    FileOutputStream dataFile = openFileOutput("telepon.dat", MODE_APPEND);
                    DataOutputStream output = new DataOutputStream(dataFile);

                    output.write(bufferNama);
                    output.write(bufferTelepon);

                    dataFile.close();

                    Toast.makeText(getBaseContext(), "Data telah disimpan",
                            Toast.LENGTH_LONG).show();
                }

                catch (IOException e){
                    Toast.makeText(getBaseContext(), "Kesalahan: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                tampilkanData();
            }
        });
        tampilkanData();
    }
    public void salinData(byte[] buffer, String data) {
//mengosongkan buffer
        for (int i = 0; i < buffer.length; i++)
            buffer[i] = 0;

//menyalin data ke buffer
        for (int i = 0; i < data.length(); i++)
            buffer[i] = (byte) data.charAt(i);
    }

    public void tampilkanData() {
        try {
            // menyiapkan file untuk dibaca
            FileInputStream dataFile = openFileInput("telepon.dat");
            DataInputStream input = new DataInputStream(dataFile);

            //menyiapkan buffer
            byte[] bufNama = new byte[30];
            byte[] bufTelepon = new byte[15];

            String infoData = "Data Telepon:\n";

            arrayKontak.clear();
            int no = 1;

            //proses membaca data
            while (input.available() > 0) {
                input.read(bufNama);
                input.read(bufTelepon);

                String dataNama = "";
                for (int i = 0; i < bufNama.length; i++)
                    dataNama = dataNama + (char) bufNama[i];

                String dataTelepon = "";
                for (int i = 0; i < bufTelepon.length; i++)
                    dataTelepon = dataTelepon + (char) bufTelepon[i];

                //format menampilkan data
                infoData = no + ". " + dataNama + " - " + dataTelepon;
                arrayKontak.add(infoData);
                no++;
            }

            //menampilkan data ke teks view
            dataFile.close();
        }
        catch (IOException e) {
            Toast.makeText(getBaseContext(), "Kesalahan: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arrayKontak);
        daftarTelepon.setAdapter(adapter);
    }
}








