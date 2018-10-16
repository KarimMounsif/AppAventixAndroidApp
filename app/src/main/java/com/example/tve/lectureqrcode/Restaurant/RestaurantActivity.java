package com.example.tve.lectureqrcode.Restaurant;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.example.tve.lectureqrcode.BDD.DataBaseHandler;
import com.example.tve.lectureqrcode.BDD.Restaurant;
import com.example.tve.lectureqrcode.Login.LoginActivity;
import com.example.tve.lectureqrcode.MainPage.MainActivity;
import com.example.tve.lectureqrcode.Parametres.SettingsActivity;
import com.example.tve.lectureqrcode.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

/**
 * Created by TVE on 31/01/2018.
 */

public class RestaurantActivity extends AppCompatActivity {

    private String TAG = RestaurantActivity.class.getSimpleName();

    private RecyclerView restaurantRecyclerView;
    private List<Restaurant> restaurantsList = new ArrayList<>();
    private Toolbar toolbar;
    private RestaurantViewAdapter rViewAdapter;
    private String serverAdress = "192.168.1.1";
    //private String serverAdress = "192.168.0.10";
    //private String serverAdress = "172.20.10.4";
    private String jsonResult;
    private JSONObject jsonRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        restaurantRecyclerView = findViewById(R.id.restaurant_recyclerView);
        rViewAdapter = new RestaurantViewAdapter(restaurantsList);
        RecyclerView.LayoutManager hLayoutManager = new LinearLayoutManager(getApplicationContext());
        restaurantRecyclerView.setLayoutManager(hLayoutManager);
        restaurantRecyclerView.setItemAnimator(new DefaultItemAnimator());
        restaurantRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        restaurantRecyclerView.setAdapter(rViewAdapter);
        new GetRestaurants().execute();

    }

    public class GetRestaurants extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPreExecute() {

        }


        protected void onPostExecute(String result) {
            prepareHistoriqueData(jsonResult);
            rViewAdapter.notifyDataSetChanged();
        }

        @Override
        protected String doInBackground(String... strings) {

            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            String url =  "http://"+serverAdress+":8080/aventix/rest/employeService/commercesNearby";
            String jsonStringRestaurant = dataBaseHandler.makeServiceCall(url);
            Log.e(TAG, "réponse de l'url: " + jsonStringRestaurant);
            if(jsonStringRestaurant!=null) {
                jsonResult = jsonStringRestaurant;
            }
            else {
                Log.e(TAG, "Json non obtenu à partir du serveur");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Erreur de récupération ! vérifiez votre connexion",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
    }

    public void prepareHistoriqueData(String jSonResult){
        try {
            String nomRestaurant;
            String adresseRestaurant;
            String telRestaurant;
            jsonRestaurants = new JSONObject(jsonResult);
            boolean validated = jsonRestaurants.getBoolean("validated");
            if (validated) {
                Log.e(TAG, "Dans le if validated");
                JSONArray responseObject = jsonRestaurants.getJSONArray("responseObject");
                List<Double> distanceList = new ArrayList<>();
                float km = 0.1F;
                for (int i = 0; i < responseObject.length(); i++) {

                    nomRestaurant = responseObject.getJSONObject(i).getString("nomCommerce");
                    adresseRestaurant = responseObject.getJSONObject(i).getString("adresseCommerce");
                    telRestaurant = responseObject.getJSONObject(i).getString("telCommerce");
                    km = km + 0.1F;
                    restaurantsList.add(new Restaurant(nomRestaurant, adresseRestaurant, telRestaurant, valueOf(km)));
                    Log.e(TAG, Integer.toString(restaurantsList.size()));
                }

            }else{
                Log.e(TAG, "Echec de recuperation des achats");
            }
        } catch (final JSONException je) {
            Log.e(TAG, "erreur de parsing Json" + je.getMessage());
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
