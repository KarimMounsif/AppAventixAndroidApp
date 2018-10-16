package com.example.tve.lectureqrcode.QRCode;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tve.lectureqrcode.BDD.DataBaseHandler;
import com.example.tve.lectureqrcode.Login.LoginActivity;
import com.example.tve.lectureqrcode.MainPage.MainActivity;
import com.example.tve.lectureqrcode.MainPage.MainActivityCommercant;
import com.example.tve.lectureqrcode.Parametres.SettingsActivity;
import com.example.tve.lectureqrcode.R;
import com.example.tve.lectureqrcode.model.Commercant;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONException;
import org.json.JSONObject;

import static android.support.v4.app.NotificationCompat.*;

public class LectureQRcodeActivity extends AppCompatActivity {

    private static final String TAG_scan = LectureQRcodeActivity.class.getSimpleName();

    private Button btnCamera;
    private Toolbar toolbar;
    private TextView tv_montant;
    private final Activity activity = this;
    private DataBaseHandler handler =null;
    //private String serverAdress = "192.168.1.1";
    //private String serverAdress = "192.168.0.10";
    private String serverAdress = "172.20.10.4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_lecture_qrcode);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_montant=findViewById(R.id.et_montant_transaction);



        btnCamera= (Button) findViewById(R.id.btn_camera);


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptTransaction();


            }
        });
    }

    private void attemptTransaction(){
        tv_montant.setError(null);

        CharSequence montant = tv_montant.getText();
        //float montant = Float.valueOf(tv_montant.getText().toString());

        boolean cancel = false;
        View focusView = null;


        if(TextUtils.isEmpty(montant)){
            tv_montant.setError("Montant manquant");
            focusView = tv_montant;
            cancel=true;
        }

        /*if(!TextUtils.isDigitsOnly(montant)){
            tv_montant.setError(("Ne rentrez que des chiffres"));
            focusView = tv_montant;
            cancel=true;
        }*/

        if(cancel){
            focusView.requestFocus();
        }

        else{
            IntentIntegrator integrator = new IntentIntegrator(activity);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
        }
    }
    public class PaiementAsyncTask extends AsyncTask<String, Void, String> {
        private int reqCode;
        private int resCode;
        private Intent data;

        public PaiementAsyncTask(int reqCode, int resCode, Intent data) {
            this.reqCode = reqCode;
            this.resCode = resCode;
            this.data = data;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObjectPaiement = new JSONObject(result);
                boolean validated = jsonObjectPaiement.getBoolean("validated");
                if (validated) {
                    addNotification();
                    Log.e(TAG_scan, "Paiement effectué");
                } else {
                    JSONObject errorMessages = jsonObjectPaiement.getJSONObject("errorMessages");
                    String erreur = errorMessages.getString("erreur");
                    if (erreur.equals("transaction impossible en week-end")) {
                        Toast.makeText(getApplicationContext(), "transaction impossible en week-end", Toast.LENGTH_LONG).show();
                        Log.e(TAG_scan, "transaction impossible en week-end");
                    }
                    else if (erreur.equals("Compte désactivé")){
                        Toast.makeText(getApplicationContext(), "Compte désactivé", Toast.LENGTH_LONG).show();
                        Log.e(TAG_scan, "Compte désactivé");
                    }
                    else if (erreur.equals("Solde insuffisant")){
                        Toast.makeText(getApplicationContext(), "Solde insuffisant", Toast.LENGTH_LONG).show();
                        Log.e(TAG_scan, "Solde insuffisant");
                    }
                    else if (erreur.equals("Solde journalier dépassé")) {
                        Toast.makeText(getApplicationContext(), "Solde journalier dépassé",Toast.LENGTH_LONG).show();
                        Log.e(TAG_scan, "Solde journalier dépassé");
                    }
                }

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Echec paiement, Vérifier votre connexion",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String code = data.getExtras().getString("SCAN_RESULT");
            String montant = tv_montant.getText().toString();
            String idCommercant = Commercant.getIdCommercant();
            handler = new DataBaseHandler();
            String url = "http://"+serverAdress+":8080/aventix/rest/commerceService/Paiement?numeroCode=" + code + "&montant=" + montant + "&idCommercant=" + idCommercant;
            final String jsonStringPaiement = handler.makeServiceCall(url);

            return jsonStringPaiement;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED){
            if(data != null){
                new PaiementAsyncTask(requestCode, resultCode, data).execute();
            }
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

    private void addNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "PaiementOK")
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setSmallIcon(R.drawable.ic_aventix_round)
                .setContentTitle("Aventix : Transaction")   //this is the title of notification
                .setColor(101)
                .setContentText("Paiement de " + tv_montant.getText() + "€ effectué avec succès" );   //this is the message showed in notification
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        //Add as notification
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(1, builder.build());
    }
}


