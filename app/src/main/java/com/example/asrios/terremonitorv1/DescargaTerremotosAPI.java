package com.example.asrios.terremonitorv1;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by asrios on 16/12/17.
 */

public class DescargaTerremotosAPI extends AsyncTask<URL, Void, ArrayList<Terremoto>> {

    public DownloadTerremotosInterface delegate;

    public interface DownloadTerremotosInterface {
        void onTerremotosDownloaded (ArrayList<Terremoto> eqList);
    }

    @Override
    protected ArrayList<Terremoto> doInBackground(URL... urls) {
        String terremotoData;
        ArrayList<Terremoto> terremotoLista = null;

        try{
            terremotoData = downloadData(urls[0]);
            terremotoLista = parseDataFromJson(terremotoData);
        }catch (IOException e){
            e.printStackTrace();
        }
        return terremotoLista ;
    }

    @Override
    protected void onPostExecute(ArrayList<Terremoto> terremotoLista ) {
        super.onPostExecute(terremotoLista );

        delegate.onTerremotosDownloaded(terremotoLista );
    }

    private ArrayList<Terremoto> parseDataFromJson(String eqsData){
        ArrayList<Terremoto> eqList = new ArrayList<>();
        try {
            JSONObject jsonObject= new JSONObject(eqsData);
            JSONArray featuresJsonArray = jsonObject.getJSONArray("features");

            for (int i = 0; i< featuresJsonArray.length(); i++){
                JSONObject featuresJsonObject = featuresJsonArray.getJSONObject(i);
                JSONObject propertiesJsonObject = featuresJsonObject.getJSONObject("properties");
                double magnitude = propertiesJsonObject.getDouble("mag");
                String place = propertiesJsonObject.getString("place");
                Long time = propertiesJsonObject.getLong("time");

                JSONObject geometryJsonObject = featuresJsonObject.getJSONObject("geometry");
                JSONArray coodinatesJsonArray = geometryJsonObject.getJSONArray("coordinates");
                String longitude = coodinatesJsonArray.getString(0);
                String latitude = coodinatesJsonArray.getString(1);

                eqList.add(new Terremoto(time, magnitude, place, longitude, latitude));
                Log.d("TERREMOTO", magnitude + ": " + place);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eqList;
    }

    private String downloadData(URL url) throws  IOException {
        String jsonResponse ="";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000/* milliseconds */);
            urlConnection.setConnectTimeout(15000 /*milliseconds */);
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream (inputStream);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }

            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private String readFromStream (InputStream inputStream) throws IOException{
        StringBuilder sb = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader (inputStreamReader);
            String line = reader.readLine ();
            while(line != null){
                sb.append(line);
                line = reader.readLine ();
            }
        }
        return sb.toString();
    }


}