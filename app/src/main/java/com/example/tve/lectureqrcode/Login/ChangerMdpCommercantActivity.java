package com.example.tve.lectureqrcode.Login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tve.lectureqrcode.BDD.DataBaseHandler;
import com.example.tve.lectureqrcode.MainPage.MainActivity;
import com.example.tve.lectureqrcode.Parametres.SettingsActivity;
import com.example.tve.lectureqrcode.R;
import com.example.tve.lectureqrcode.model.Commercant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by thoma on 15/03/2018.
 */

public class ChangerMdpCommercantActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private static final String TAG_changermdpCo = ChangerMdpCommercantActivity.class.getSimpleName();

    private DataBaseHandler handler=null;

    private Button button_changement_mdp;
    private EditText nouveau_mdp1_co;
    private EditText nouveau_mdp2_co;
    private ChangerMdpCommercantActivity.CommercantChangementMDP changementMDPAuth=null;

    private String idCommercant = Commercant.getIdCommercant();
    //private String serverAdress = "192.168.1.1";
    //private String serverAdress = "192.168.0.10";
    private String serverAdress = "172.20.10.4";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changermdp_commercant);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button_changement_mdp=findViewById(R.id.submit_changement_mdp_commercant);

        nouveau_mdp1_co=findViewById(R.id.nouveau_pw1_commercant);
        nouveau_mdp2_co=findViewById(R.id.nouveau_pw2_commercant);

        button_changement_mdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attempt_changement_mdp();
            }
        });
    }

    private void attempt_changement_mdp(){

        String nouveau_pw1=nouveau_mdp1_co.getText().toString();
        String nouveau_pw2=nouveau_mdp2_co.getText().toString();

        boolean cancel=false;
        View focusview=null;


        if(!isPasswordValid(nouveau_pw1)){
            nouveau_mdp1_co.setError("Votre nouveau mot de passe doit contenir 4 caractères minimum");
            focusview = nouveau_mdp1_co;
            cancel = true;
        }
        if(!isPasswordEquivalent(nouveau_pw1, nouveau_pw2)){
            nouveau_mdp2_co.setError("Les mots de passe ne correspondent pas");
            focusview = nouveau_mdp2_co;
            cancel = true;
        }


        if(cancel){
            focusview.requestFocus();
        }
        else {
            changementMDPAuth = new ChangerMdpCommercantActivity.CommercantChangementMDP(nouveau_pw1);
            changementMDPAuth.execute();
        }


    }

    private boolean isPasswordValid(String password){
        return password.length()>4;
    }

    private boolean isPasswordEquivalent(String password1, String password2){
        if(password1.equals(password2)){
            return true;
        }
        return false;
    }


    public class CommercantChangementMDP extends AsyncTask<Void, Void, Boolean> {

        private final String mNouveau_pw;

        CommercantChangementMDP(String nouveau_pw){

            mNouveau_pw=nouveau_pw;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            changementMDPAuth=null;
            if(success)
            {
                finish();
            }
            super.onPostExecute(success);
        }

        @Override
        protected void onCancelled() {
            changementMDPAuth=null;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            handler=new DataBaseHandler();

            String url = "http://"+serverAdress+":8080/aventix/rest/commerceService/updateMdp?idGerant="+idCommercant+"&newMdp="+mNouveau_pw;
            final String jsonString = handler.makeServiceCall(url);
            try{
                JSONObject jsonObject = new JSONObject(jsonString);
                boolean validated = jsonObject.getBoolean("validated");
                if(validated){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Changement de mot de passe avec succès ! Reconnectez vous" , Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent intent = new Intent(
                            ChangerMdpCommercantActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else  {
                    Toast.makeText(ChangerMdpCommercantActivity.this, "Le changement de mot de passe a échoué", Toast.LENGTH_LONG).show();
                    Intent intentfail = new Intent(ChangerMdpCommercantActivity.this, ChangerMdpActivity.class);
                    startActivity(intentfail);
                }
            } catch (JSONException e) {
                Log.e(TAG_changermdpCo, "Problème lors du parsing JSON");
            }

            return true;
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
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.deconnection:
                Intent deco = new Intent(this, LoginActivity.class);
                startActivity(deco);
                break;

            case R.id.retour_arriere:
                Intent retour = new Intent(this, LoginActivity.class);
                startActivity(retour);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}