package com.example.km.qrcodepay.Login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.km.qrcodepay.BDD.DataBaseHandler;
import com.example.km.qrcodepay.MainPage.MainActivity;
import com.example.km.qrcodepay.MainPage.MainActivityCommercant;
import com.example.km.qrcodepay.R;
import com.example.km.qrcodepay.model.Commercant;
import com.example.km.qrcodepay.model.employeQr;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public  class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final String TAG_login = LoginActivity.class.getSimpleName();
    private static Commercant com;
    private UserLoginTask mAuthTask = null;
    private CommercantLoginTask mCommercantAuthTask = null;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    private Button commercantCo;
    private String serverAdress = "192.168.1.1";
    //private String serverAdress = "192.168.0.10";
    //private String serverAdress = "172.20.10.4";
    boolean responseOnPostExecute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);


        mPasswordView = (EditText) findViewById(R.id.password);
        /**mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLoginEmploye();
                    return true;
                }
                return false;
            }
        });
         */

        commercantCo=findViewById(R.id.commercant_login);
        commercantCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                attemptLoginCommercant();
            }
        });


        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(buttonClick);
                attemptLoginEmploye();
                //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //startActivity(intent);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }





    /**
     * Tentative de login. S'il y a des erreurs de forme (email invalides, champs manquants, etc.)
     * les erreurs sont présentés à l'utilisateur et aucune tentative de login est faite
     */
    private void attemptLoginEmploye() {
        if (mAuthTask != null) {
            return;
        }

        // Reset des erreurs.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Stocker les valeurs saisies
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Est ce que le mot de passe est correct ?
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // email valide?
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // erreur, focus sur la zone avec l'erreur
            focusView.requestFocus();
        } else {
            // Montre une barre de progression, et lance une tâche de fond pour
            // la tentative de connexion

            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute();
        }
    }


    /**
     * Tentative de login. S'il y a des erreurs de forme (email invalides, champs manquants, etc.)
     * les erreurs sont présentés à l'utilisateur et aucune tentative de login est faite
     */
    private void attemptLoginCommercant() {
        if (mAuthTask != null) {
            return;
        }

        // Reset des erreurs.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Stocker les valeurs saisies
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Est ce que le mot de passe est correct ?
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // email valide?
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // erreur, focus sur la zone avec l'erreur
            focusView.requestFocus();
        } else {
            // Montre une barre de progression, et lance une tâche de fond pour
            // la tentative de connexion

            showProgress(true);
            mCommercantAuthTask = new CommercantLoginTask(email, password);
            mCommercantAuthTask.execute();
        }
    }

    private boolean isEmailValid(CharSequence email) {

        // TODO : Incrémenter une logique avec vérification de l'email dans la base de données
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private boolean isPasswordValid(String password) {
        //TODO: Rajouter une logique de vérification avec la base de données
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Tâche asynchrone qui gère l'authentification
     */
    public class UserLoginTask extends AsyncTask<String, Void, String> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected String doInBackground(String... strings) {
            DataBaseHandler baseHandler = new DataBaseHandler();
            String jsonStringAuthentification = null;

            jsonStringAuthentification = baseHandler.makeServiceCall(
                    "http://" + serverAdress + ":8080/aventix/rest/employeService/authEmploye?mailEmploye=" + mEmail + "&mdpEmploye=" + mPassword);


            return jsonStringAuthentification;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject successMessages;
            JSONObject responseObject;
            JSONObject errorMessages;
            if ((result == null)) {
                Intent intent = new Intent(
                        LoginActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Service indisponible, Veuillez réessayer plus tard", Toast.LENGTH_LONG).show();

            }else {
                try {
                    JSONObject jsonObjectAuthentification = new JSONObject(result);
                    Log.e(TAG_login, jsonObjectAuthentification.toString());
                    boolean validated = jsonObjectAuthentification.getBoolean("validated");
                    if (validated) {
                        successMessages = jsonObjectAuthentification.getJSONObject("successMessages");
                        responseObject = jsonObjectAuthentification.getJSONObject("responseObject");

                        JSONObject qr = responseObject.getJSONObject("qr");
                        JSONObject e = responseObject.getJSONObject("e");
                        employeQr eqr = new employeQr();

                        /**Binding POJOS/JSON*********/
                        Log.e(TAG_login, responseObject.getJSONObject("qr").toString());
                        eqr.setNumeroCode(qr.getString("numeroCode"));
                        eqr.setStatusQrCode(qr.getInt("statusQrCode"));
                        eqr.setIdEmploye(e.getString("idEmploye"));
                        eqr.setNomEmploye(e.getString("nomEmploye"));
                        eqr.setPrenomEmploye(e.getString("prenomEmploye"));
                        eqr.setCiviliteEmploye(e.getString("civiliteEmploye"));
                        eqr.setDdnEmploye(e.getString("ddnEmploye"));
                        eqr.setTypeEmploye(e.getString("typeEmploye"));
                        eqr.setTelEmploye(e.getString("telEmploye"));
                        eqr.setMailEmploye(e.getString("mailEmploye"));
                        eqr.setSoldeEmploye((float) e.getDouble("soldeEmploye"));
                        eqr.setMdpEmploye(e.getString("mdpEmploye"));
                        eqr.setDateCreationCompteEmploye(e.getString("dateCreationCompteEmploye"));
                        eqr.setDateDerniereConnexionEmploye(e.getString("dateDerniereConnexionEmploye"));
                        eqr.setIdEntreprise(e.getString("idEntreprise"));

                        String response = successMessages.getString("response");
                        if (response.equals("authentication OK")) {
                            eqr.setStatusEmploye(e.getInt("statusCompteEmploye"));
                            Intent intent = new Intent(
                                    LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (response.equals("premiere connexion")) {
                            Log.e(TAG_login, "premiere connexion");
                            Intent intentnewCo = new Intent(LoginActivity.this, ChangerMdpActivity.class);
                            startActivity(intentnewCo);
                        }
                    } else {
                        errorMessages = jsonObjectAuthentification.getJSONObject("errorMessages");
                        String erreur = errorMessages.getString("erreur");
                        if (erreur.equals("utilisateur desactive")) {
                            Intent intent = new Intent(
                                    LoginActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Votre compte a été désactivé", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else if (erreur.equals("login ou mdp incorrects")) {
                            Log.e(TAG_login, "login ou mdp incorrects");
                            Intent intent = new Intent(
                                    LoginActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Login ou mot de passe incorrects", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                } catch (JSONException e) {
                    Log.e(TAG_login, "Json exception");
                    Intent intent = new Intent(
                            LoginActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Service indisponible, Veuillez réessayer plus tard", Toast.LENGTH_LONG).show();
                        }
                    });
                }
        }
            mAuthTask = null;
            showProgress(false);
            /*
            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }*/
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /**
     * Tâche asynchrone qui gère l'authentification COMMERCANT
     */
    public class CommercantLoginTask extends AsyncTask<String , Void, String > {

        private final String mEmail;
        private final String mPassword;

        CommercantLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected String  doInBackground(String ... strings) {
            DataBaseHandler baseHandler = new DataBaseHandler();
            JSONObject successMessages = null;
            JSONObject errorMessage = null;
            JSONObject responseObject = null;

            String jsonString = baseHandler.makeServiceCall(
                    "http://"+serverAdress+":8080/aventix/rest/commerceService/authGerant?mailGerant="+mEmail+"&mdpGerant="+mPassword);

            try{
                JSONObject jsonObjectAuthentificationGerant = new JSONObject(jsonString);
                Log.e(TAG_login, jsonObjectAuthentificationGerant.toString());

                boolean validated = jsonObjectAuthentificationGerant.getBoolean("validated");
                if(validated){
                    successMessages = jsonObjectAuthentificationGerant.getJSONObject("successMessages");
                    responseObject = jsonObjectAuthentificationGerant.getJSONObject("responseObject");
                    com = new Commercant();
                    com.setIdCommercant(responseObject.getJSONObject("g").getString("idGerant"));
                    com.setCiviliteCommercant(responseObject.getJSONObject("g").getString("civiliteGerant"));
                    com.setPrenomCommercant(responseObject.getJSONObject("g").getString("prenomGerant"));
                    com.setNomCommercant(responseObject.getJSONObject("g").getString("nomGerant"));
                    String response = successMessages.getString("response");
                    if(response.equals("premiere connexion")){
                        Intent intentNewCo=new Intent(LoginActivity.this, ChangerMdpCommercantActivity.class);
                        startActivity(intentNewCo);
                    }
                    else if(response.equals("authentication OK")){
                        Intent intent = new Intent(
                                LoginActivity.this, MainActivityCommercant.class);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    errorMessage = jsonObjectAuthentificationGerant.getJSONObject("errorMessages");
                    String erreur = errorMessage.getString("erreur");
                    if(erreur.equals("utilisateur désactivé")){
                        Intent intent = new Intent(
                                LoginActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Votre compte a été désactivé" , Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    else if(erreur.equals("login ou mdp incorrects")){
                        Intent intent = new Intent(
                                LoginActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Login ou mot de passe incorrects" , Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }


            }catch(JSONException e){
                Log.e(TAG_login, "erreur de parsing JSON");
                    Intent intent = new Intent(
                            LoginActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "Service indisponible, Veuillez réessayer plus tard", Toast.LENGTH_LONG).show();
            }


            return jsonString;
        }
        @Override
        protected void onPostExecute(String result) {
            if((result == null)) {
                Intent intent = new Intent(
                        LoginActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Service indisponible, Veuillez réessayer plus tard", Toast.LENGTH_LONG).show();
            }
            mAuthTask = null;
            showProgress(false);
           /*
            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }*/
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

    }
}

