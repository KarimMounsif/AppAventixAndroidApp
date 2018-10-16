package com.example.tve.lectureqrcode.QRCode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tve.lectureqrcode.BDD.DataBaseHandler;
import com.example.tve.lectureqrcode.BDD.Employe;
import com.example.tve.lectureqrcode.Login.LoginActivity;
import com.example.tve.lectureqrcode.MainPage.MainActivity;
import com.example.tve.lectureqrcode.Parametres.SettingsActivity;
import com.example.tve.lectureqrcode.R;
import com.example.tve.lectureqrcode.model.Commercant;
import com.example.tve.lectureqrcode.model.employeQr;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

public class GenerateQRCodeActivity extends AppCompatActivity {

    private static final String TAG = GenerateQRCodeActivity.class.getSimpleName();

    private Button btn_generateQRcode;
    private ImageView imageQRcode;

    private String qrCode;
    private String testQRCode;

    private Toolbar toolbar;
    private String serverAdress = "192.168.1.1";
    //private String serverAdress = "192.168.0.10";
    //private String serverAdress = "172.20.10.4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_generate_qrcode);

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageQRcode=(ImageView)findViewById(R.id.imageQRCode);
        new GenerateCodeAsync().execute();
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = 1F;
        getWindow().setAttributes(layout);
    }

    public class GenerateCodeAsync extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onPostExecute(String result) {
            if(result == null){
                Intent intent = new Intent(
                        GenerateQRCodeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Site en maintenance, Veuillez réessayer plus tard", Toast.LENGTH_LONG).show();
            }
            if(result.equals("noQRCode")){
                Intent intent = new Intent(
                        GenerateQRCodeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(),
                        "Veuillez réessayez plus tard, votre QR code sera bientôt disponible",
                        Toast.LENGTH_LONG).show();
            }
            else{
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(qrCode, BarcodeFormat.QR_CODE, 250, 250);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imageQRcode.setImageBitmap(bitmap);
                } catch (WriterException we) {
                    we.printStackTrace();
                }
            }
        }



        @Override
        protected String doInBackground(String... strings) {
            String idEmploye = employeQr.getIdEmploye();
            DataBaseHandler handler = new DataBaseHandler();
            String url = "http://"+serverAdress+":8080/aventix/rest/employeService/getQrCode?idEmploye="+idEmploye;
            String jsonStringQrCode = handler.makeServiceCall(url);
            try {
                JSONObject jsonObjectQrCode = new JSONObject(jsonStringQrCode);
                Log.e(TAG, jsonStringQrCode);
                boolean validated = jsonObjectQrCode.getBoolean("validated");
                if (validated) {
                    Log.e(TAG, "Dans le if validated");
                    JSONObject responseObject = jsonObjectQrCode.getJSONObject("responseObject");
                    qrCode = responseObject.getString("QrCode");
                    Log.e(TAG, qrCode);
                } else {
                    jsonStringQrCode = "noQRCode";
                }

            } catch (JSONException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Echec de recuperation ! Vérifiez votre connexion",
                                Toast.LENGTH_LONG);
                    }
                });
            }
            return jsonStringQrCode;
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
                Intent retour = new Intent(this, MainActivity.class);
                startActivity(retour);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
