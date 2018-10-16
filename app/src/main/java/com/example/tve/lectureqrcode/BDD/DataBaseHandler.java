package com.example.tve.lectureqrcode.BDD;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.tve.lectureqrcode.Login.LoginActivity;

import org.apache.http.params.HttpParams;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;
import java.util.concurrent.TimeoutException;

import javax.security.auth.login.LoginException;

/**
 * Created by TVE on 30/01/2018.
 * Le handler va gérer la connexion à la base de données
 * Par la méthode makeServiceCall(String reqUrl)
 * reqUrl étant l'url de notre base  de données
 */

public class DataBaseHandler {
    private static final String TAG_handler = DataBaseHandler.class.getSimpleName();

    public DataBaseHandler(){

    }

    public String  makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(6000);
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            response = convertStreamToString(inputStream);
        } catch (MalformedURLException e) {
            Log.e(TAG_handler, "Exception ttttttt: " + e.getMessage());
            return "EchecGET";
        } catch (IOException e) {
            Log.e(TAG_handler, "Exception xxxxxxxx: " + e.getMessage());
            return "EchecGET";
        } catch (Exception e){
            Log.e(TAG_handler, "Exception yyyyyyyyy: " + e.getMessage());
            e.printStackTrace();
            return "EchecGET";
        }
        return response;
    }

    private  String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String ligne;
        try {
            while ((ligne=bufferedReader.readLine())!=null)
            {
                stringBuilder.append(ligne).append('\n');
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try{
                inputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
