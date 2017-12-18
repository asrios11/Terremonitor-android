package com.example.asrios.terremonitorv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UltimosSieteActivity extends AppCompatActivity implements DescargaTerremotosAPI.DownloadTerremotosInterface {

    public final static String TERREMOTO_ACTUAL = "terremoto_actual";
    ListView terremotoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultimos_siete);

        terremotoListView = (ListView) findViewById(R.id.lista_terremoto_view3);

        DescargaTerremotosAPI downloadTerremoto = new DescargaTerremotosAPI(); //Objeto que descarga los Json
        downloadTerremoto.delegate = this;

        try{
            downloadTerremoto.execute(new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.geojson"));
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onTerremotosDownloaded(ArrayList<Terremoto> eqList) {

        final AdaptadorTerremoto eqAdapter = new AdaptadorTerremoto(this, R.layout.lista_terremoto_elemento, eqList);
        terremotoListView.setAdapter(eqAdapter);

        terremotoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Terremoto terremotoActual = eqAdapter.getItem(position);

                Intent intent = new Intent(UltimosSieteActivity.this, DetallesActivity.class);
                intent.putExtra(TERREMOTO_ACTUAL, terremotoActual);

                startActivity(intent);
            }
        });
    }
}
