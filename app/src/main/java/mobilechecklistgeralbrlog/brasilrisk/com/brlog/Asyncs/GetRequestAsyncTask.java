package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Asyncs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;


import com.brasilrisk.mobilechecklistgeralbrlog.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.BrasilRisk;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Utils.Loads;


/**
 * Created by evox on 26/10/16.
 */

public class GetRequestAsyncTask extends AsyncTask<Void, Void, String> {


    String metodo;
    String Response,Cache;
    mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnPostExecute OnPostExecute;
    Context context;
    String UrlString;
    HttpURLConnection urlConnection;
    String message;
    boolean EnableAlert;
    ProgressDialog progressDialog;

    public void setMessage(String message) {
        this.message = message;
    }

    public GetRequestAsyncTask(Context context, String metodo, String Cache) {
        message = "Carregando...";
        this.context = context;
        this.metodo = metodo;
        this.Cache = Cache;
        UrlString = BrasilRisk.URL_Conetcion+metodo;
        progressDialog = Loads.ProgressDialog(context,message);

        //urlConnection.addRequestProperty("UnidadeId", "2");
        //urlConnection.addRequestProperty("Identificador", "celsinhoricardo@gmail.com");
    }

    protected void onPreExecute() {
        if(EnableAlert)
            progressDialog.show();
    }
    public void EnableProgressdialog(String message){
        this.message = message;
        EnableAlert =true;

    }
    protected String doInBackground(Void... urls) {

        // Do some validation her
        try {
            URL url = new URL(UrlString);
            //URL url = new URL("http://beta-audios.alomarketplace.com.br/RestApi/getAudiosPorCategoriaST");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(40000);
            urlConnection.setDoInput(true);
            urlConnection.setReadTimeout(40000);
            //urlConnection.setRequestProperty("token", "Basic ZXZveDpwbGF0YWZvcm1hLTIwMTY=");

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                //Response =stringBuilder.toString();
                return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }


    @Override
    protected void onPostExecute(String result) {
       // Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        setResponse(result);
        if(EnableAlert)
            progressDialog.dismiss();
        if(OnPostExecute!=null)
        this.OnPostExecute.OnPostExecute();

    }

    public void AddFirstParam(String key, String value) {
        UrlString +="?"+key+"="+value;

    }
    public void AddParam(String key, String value) {


        UrlString +="&"+key+"="+value;
        //urlConnection.addRequestProperty(nome,value);

    }

    public void setResponse(String response) {
        Response = response;
        if(Cache!=null && Cache!="") {
            SharedPreferences preferences = context.getSharedPreferences(context.getResources().getString(R.string.sharedpreferences), 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Cache, response);
            editor.commit();
        }
    }

    public String getResponse() {
        return Response;
    }

    public void setOnPostExecute(mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnPostExecute onPostExecute) {
        OnPostExecute = onPostExecute;
    }


}
