package com.example.tve.lectureqrcode.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.example.tve.lectureqrcode.Historique.TransactionCommercantActivity;
import com.example.tve.lectureqrcode.Login.LoginActivity;
import com.example.tve.lectureqrcode.Parametres.SettingsActivity;
import com.example.tve.lectureqrcode.QRCode.LectureQRcodeActivity;
import com.example.tve.lectureqrcode.R;
import com.example.tve.lectureqrcode.model.Commercant;

/**
 * Created by thoma on 04/03/2018.
 */

public class MainActivityCommercant extends AppCompatActivity{

    private static final String TAG = MainActivityCommercant.class.getSimpleName();

    private Toolbar toolbar;

    private CardView scanner_qrcode;
    private CardView transaction_commercant;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_commercant_v2);


        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Commercant.getCiviliteCommercant() + " " +Commercant.getPrenomCommercant()+ " " + Commercant.getNomCommercant());

        scanner_qrcode=findViewById(R.id.scan_qrcode);
        transaction_commercant=findViewById(R.id.transaction_commercant);



        scanner_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scannerQRCode = new Intent(MainActivityCommercant.this, LectureQRcodeActivity.class);
                startActivity(scannerQRCode);
            }
        });

        transaction_commercant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historiqueTransaction= new Intent(MainActivityCommercant.this, TransactionCommercantActivity.class);
                startActivity(historiqueTransaction);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        //getActionBar().setDisplayHomeAsUpEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            /**
             * appui sur le bouton paramètre, déconnexion ou la flèche retour en arrière
             */
            /*
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;*/

            case R.id.deconnection:
                Intent deco = new Intent(this, LoginActivity.class);
                startActivity(deco);
                break;
/*
            case R.id.retour_arriere:
                Intent retour = new Intent(this, LoginActivity.class);
                startActivity(retour);
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {

    }
}
