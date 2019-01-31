package com.example.km.qrcodepay.Historique;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.km.qrcodepay.BDD.DataBaseHandler;
import com.example.km.qrcodepay.BDD.Historique;
import com.example.km.qrcodepay.Login.LoginActivity;
import com.example.km.qrcodepay.MainPage.MainActivity;
import com.example.km.qrcodepay.R;
import com.example.km.qrcodepay.model.employeQr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class HistoriqueActivity extends AppCompatActivity {

    private static final String TAG_historique = HistoriqueActivity.class.getSimpleName();
    private RecyclerView historiqueRecyclerView;
    private List<Historique> historiqueList = new ArrayList<>();
    private Toolbar toolbar;
    private HistoriqueViewAdapter hViewAdapter;
    private String serverAdress = "192.168.1.1";
    //private String serverAdress = "192.168.0.10";
    //private String serverAdress = "172.20.10.4";
    private String jsonResult;
    private JSONObject jsonObjectSolde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_historique_employe);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


            historiqueRecyclerView = findViewById(R.id.historique_recyclerView);
            hViewAdapter = new HistoriqueViewAdapter(historiqueList);
            RecyclerView.LayoutManager hLayoutManager = new LinearLayoutManager(getApplicationContext());
            historiqueRecyclerView.setLayoutManager(hLayoutManager);
            historiqueRecyclerView.setItemAnimator(new DefaultItemAnimator());
            historiqueRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            historiqueRecyclerView.setAdapter(hViewAdapter);
            new GetHistorique().execute();

    }



    public class GetHistorique extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {
            prepareHistoriqueData(jsonResult);
            hViewAdapter.notifyDataSetChanged();
        }

        @Override
        protected String doInBackground(String... strings) {

            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            String idEmploye = employeQr.getIdEmploye();
            String url =  "http://"+serverAdress+":8080/aventix/rest/employeService/Achats?idEmploye="+idEmploye;
            String jsonStringHistorique = dataBaseHandler.makeServiceCall(url);
            Log.e(TAG_historique, "réponse de l'url: " + jsonStringHistorique);
            if(jsonStringHistorique!=null) {
                jsonResult = jsonStringHistorique;
            }
            else {
                Log.e(TAG_historique, "Json non obtenu à partir du serveur");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "regardez le Logcat pour des erreurs possibles ",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
    }


    public void prepareHistoriqueData(String jSonResult){
        try {
            String dateTransaction;
            String montantTransaction;
            String nomCommerce;
            jsonObjectSolde = new JSONObject(jsonResult);
            boolean validated = jsonObjectSolde.getBoolean("validated");
            if (validated) {
                Log.e(TAG_historique, "Dans le if validated");
                JSONArray responseObject = jsonObjectSolde.getJSONArray("responseObject");
                for (int i = 0; i < responseObject.length(); i++) {
                    dateTransaction = responseObject.getJSONObject(i).getString("montantAchat");
                    montantTransaction = responseObject.getJSONObject(i).getString("dateAchat");
                    nomCommerce = responseObject.getJSONObject(i).getString("nomCommerce");
                    historiqueList.add(new Historique(dateTransaction, montantTransaction, nomCommerce));
                    Log.e(TAG_historique, Integer.toString(historiqueList.size()));
                }

            }else{
                Log.e(TAG_historique, "Echec de recuperation des achats");
            }
        } catch (final JSONException je) {
            Log.e(TAG_historique, "erreur de parsing Json" + je.getMessage());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "erreur de parsing json: " + je.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
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
