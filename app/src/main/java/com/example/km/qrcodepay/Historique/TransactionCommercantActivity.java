package com.example.km.qrcodepay.Historique;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.example.km.qrcodepay.BDD.HistoriqueCommercant;
import com.example.km.qrcodepay.Login.LoginActivity;
import com.example.km.qrcodepay.MainPage.MainActivityCommercant;
import com.example.km.qrcodepay.R;
import com.example.km.qrcodepay.model.Commercant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thoma on 04/03/2018.
 */

public class TransactionCommercantActivity extends AppCompatActivity {

    private static final String TAG_historique = HistoriqueActivity.class.getSimpleName();
    private RecyclerView historiqueRecyclerView;
    private List<HistoriqueCommercant> historiqueList = new ArrayList<>();
    private Toolbar toolbar;
    private HistoriqueCommercantViewAdapter hViewAdapter;
    private String serverAdress = "192.168.1.1";
    //private String serverAdress = "192.168.0.10";
    //private String serverAdress = "172.20.10.4";
    private String jsonResult;
    private JSONObject jsonObjectSolde;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique) ;

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        historiqueRecyclerView = findViewById(R.id.historique_recyclerView);
        hViewAdapter = new HistoriqueCommercantViewAdapter(historiqueList);
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

        protected void onPostExecute(String result) {
            prepareHistoriqueData(jsonResult);
            hViewAdapter.notifyDataSetChanged();
        }

        @Override
        protected String doInBackground(String... strings) {

            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            String idCommerce = Commercant.getIdCommercant();
            String url =  "http://"+serverAdress+":8080/aventix/rest/commerceService/Transactions?idCommerce="+idCommerce;
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
            jsonObjectSolde = new JSONObject(jsonResult);
            boolean validated = jsonObjectSolde.getBoolean("validated");
            if (validated) {
                Log.e(TAG_historique, "Dans le if validated");
                JSONObject responseObject = jsonObjectSolde.getJSONObject("responseObject");
                for (int i = 0; i < responseObject.names().length(); i++) {
                    dateTransaction = responseObject.names().getString(i);
                    montantTransaction = responseObject.getString(responseObject.names().getString(i));
                    historiqueList.add(new HistoriqueCommercant(dateTransaction, montantTransaction));
                    Log.e(TAG_historique, Integer.toString(historiqueList.size()));
                }
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Echec de récupération ! Vérifiez votre connexion",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (final JSONException je) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Echec de récupération ! Vérifiez votre connexion",
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

            case R.id.retour_arriere:
                Intent retour = new Intent(this, MainActivityCommercant.class);
                startActivity(retour);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}


