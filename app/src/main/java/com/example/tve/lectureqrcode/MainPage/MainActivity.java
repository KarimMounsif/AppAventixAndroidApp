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

import com.example.tve.lectureqrcode.ConsulterSolde.ConsulterSoldeActivity;
import com.example.tve.lectureqrcode.Historique.HistoriqueActivity;
import com.example.tve.lectureqrcode.Login.LoginActivity;
import com.example.tve.lectureqrcode.Parametres.SettingsActivity;
import com.example.tve.lectureqrcode.QRCode.GenerateQRCodeActivity;
import com.example.tve.lectureqrcode.R;
import com.example.tve.lectureqrcode.Restaurant.RestaurantActivity;
import com.example.tve.lectureqrcode.model.employeQr;

/**
 * Created by TVE on 31/01/2018.
 */

public class MainActivity extends AppCompatActivity{

    private CardView voirQRCode;
    private CardView consultSolde;
    private CardView listRestaurant;
    private CardView historique;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);


        voirQRCode = findViewById(R.id.generateQRcodeCardView);
        consultSolde = findViewById(R.id.consult_soldeCardeView);
        listRestaurant = findViewById(R.id.list_restaurantCardView);
        historique = findViewById(R.id.transaction_employeCardView);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(employeQr.getCiviliteEmploye() + " " +employeQr.getPrenomEmploye()+ " " + employeQr.getNomEmploye());

        /**
         * Listeners sur les boutons
         */
        voirQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        GenerateQRCodeActivity.class);
                startActivity(intent);
            }
        });

        consultSolde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        ConsulterSoldeActivity.class);
                startActivity(intent);
            }
        });

        listRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        RestaurantActivity.class);
                startActivity(intent);

            }
        });

        historique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        HistoriqueActivity.class);
                startActivity(intent);
            }
        });


    }

    /**
     *
     * Gestion de la toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            /**
             * Appui sur le bouton retour arriere du menu
             */

            /*case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;*/

            case R.id.deconnection:
                Intent deco = new Intent(this, LoginActivity.class);
                startActivity(deco);
                break;
            /*
            case R.id.retour_arriere:
                Intent retour= new Intent(this, LoginActivity.class);
                startActivity(retour);
                break;
                */
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {

    }
}
