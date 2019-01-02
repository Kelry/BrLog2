package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Asyncs;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.BrasilRisk;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Utils.Loads;

/**
 * Created by evox on 03/11/16.
 */

public class PostRequestAsyncTask extends AsyncTask<Void, Void, String> {
    HttpURLConnection urlConnection;
    ProgressDialog progressDialog;

    mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnPostExecute OnPostExecute;
    String requestBody;
    Context context;
    String metodo;
    String Response;
    Uri.Builder builder;
    boolean EnableAlert;
    String message;
    boolean isJson;

    public void setJson(String json) {

        requestBody = json;
        isJson = true;
    }

    Map<String, String> params;
    public void setMessage(String message) {
        this.message = message;
    }

    public PostRequestAsyncTask(Context context, String metodo) {
        message = "Enviando...";
        this.context = context;
        this.metodo = metodo;
        builder = new Uri.Builder();
        params = new HashMap<>();
        progressDialog = Loads.ProgressDialog(context,message);

/*
        try {
            url = new URL(DadosEmpresa.URL+metodo);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
    protected void onPreExecute() {

        if(EnableAlert)
        progressDialog.show();

    }
    @Override
    protected String doInBackground(Void... voids) {
        // encode parameters
        try {
        Iterator entries = params.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            builder.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
            entries.remove();
        }
        if(!isJson)
        requestBody = builder.build().getEncodedQuery();


            URL url = new URL(BrasilRisk.URL_Conetcion+metodo);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(20000);
            urlConnection.setDoInput(true);
            urlConnection.setReadTimeout(20000);
            //urlConnection.setRequestProperty("token", "Basic ZXZveDpwbGF0YWZvcm1hLTIwMTY=");
            //urlConnection.setRequestProperty("Content-Type", "application/json");
            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(requestBody);
            writer.flush();
            writer.close();
            outputStream.close();

            InputStream inputStream;
            // get stream
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
            // parse stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp, response = "";
            while ((temp = bufferedReader.readLine()) != null) {
                response += temp;
            }

            return response;
        } catch (IOException e) {
            return e.toString();
        }
    }

    public void AddParam(String key, String value) {

        params.put(key,value);

    }

    @Override
    protected void onPostExecute(String result) {
        if (result == null) {
            result = "ERRO";
        }
        setResponse(result);
        if(EnableAlert)
        progressDialog.dismiss();
        if(OnPostExecute!=null)
        this.OnPostExecute.OnPostExecute();

    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getResponse() {
        return Response;
    }

    public void setOnPostExecute(mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnPostExecute onPostExecute) {
        OnPostExecute = onPostExecute;
    }
    public void EnableProgressdialog(){
        EnableAlert =true;

    }
}
