package com.example.km.qrcodepay.ConsulterSolde;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.km.qrcodepay.BDD.DataBaseHandler;
import com.example.km.qrcodepay.BDD.Solde;
import com.example.km.qrcodepay.Login.LoginActivity;
import com.example.km.qrcodepay.MainPage.MainActivity;
import com.example.km.qrcodepay.R;
import com.example.km.qrcodepay.model.employeQr;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TVE on 31/01/2018.
 */

public class ConsulterSoldeActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private TextView soldeMoisTV;
    private TextView soldeJourTV;
    private String soldeMois;
    private String soldeJour;
    private List<Solde> soldeList = new ArrayList<>();
    private String serverAdress = "192.168.1.1";
    //private String serverAdress = "192.168.0.10";
    //private String serverAdress = "172.20.10.4";
    private static final String TAG_consulter_solde = ConsulterSoldeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_solde);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        soldeMoisTV = findViewById(R.id.tvSoldeMoisResult);
        soldeJourTV = findViewById(R.id.tvSoldeJourResult);
        new GetSolde().execute();
    }

    public class GetSolde extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String result) {
            if(result == null){
                Intent intent = new Intent(
                        ConsulterSoldeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Merci de bien vérifier votre connexion", Toast.LENGTH_LONG).show();
            }
            else {
                soldeMoisTV.setText(soldeMois + "€");
                soldeJourTV.setText(soldeJour + "€");
            }
        }


        @Override
        protected String doInBackground(String... strings) {

            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            String idEmploye = employeQr.getIdEmploye();
            String url =  "http://"+serverAdress+":8080/aventix/rest/employeService/Solde?idEmploye="+idEmploye;
            String jsonStringSolde = dataBaseHandler.makeServiceCall(url);
            Log.e(TAG_consulter_solde, "réponse de l'url: " + jsonStringSolde );
            if(jsonStringSolde != null) {
                try {
                    JSONObject jsonObjectSolde = new JSONObject(jsonStringSolde);
                    boolean validated = jsonObjectSolde.getBoolean("validated");
                    if (validated) {
                        Log.e(TAG_consulter_solde, "Dans le if validated");
                        JSONObject responseObject = jsonObjectSolde.getJSONObject("responseObject");
                        soldeMois = Float.toString((float)responseObject.getDouble("Solde total"));
                        soldeJour = Float.toString((float)responseObject.getDouble("Solde Journalier"));
                        Log.e(TAG_consulter_solde, "soldeMois =" + soldeMois + "\nsoldeJour"+soldeJour);
                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Echec de recuperation du solde",
                                        Toast.LENGTH_LONG);
                            }
                        });
                        Log.e(TAG_consulter_solde, "Echec de recuperation du solde");
                    }
                } catch (JSONException e) {
                    Log.e(TAG_consulter_solde, "erreur lors du parsing JSON");
                    Intent intent = new Intent(
                            ConsulterSoldeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "Merci de bien vérifier votre connexion", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Log.e(TAG_consulter_solde, "json non obtenu à partir du serveur");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Regardez le LogCat pour trouver des erreurs possibles",
                                Toast.LENGTH_LONG);
                    }
                });
            }

            return jsonStringSolde;
        }
    }

    /**
     *
     * Gestion de la toolbar
     */
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
             * Appui sur le bouton retour arriere du menu
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

            case R.id.retour_arriere:
                Intent retour= new Intent(this, MainActivity.class);
                startActivity(retour);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
