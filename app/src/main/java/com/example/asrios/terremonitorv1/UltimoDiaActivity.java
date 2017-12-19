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


/**
 * <pre>
 * Clase UltimoDiaActivity
 *
 * Clase para visualizar la lista de terremotos que se ocurrieron en el ultima día
 * @author Alexandro Sánchez Rios
 * @version 1.0
 * </pre>
 */

public class UltimoDiaActivity extends AppCompatActivity implements DescargaTerremotosAPI.DownloadTerremotosInterface{

    public final static String TERREMOTO_ACTUAL = "terremoto_actual";
    ListView terremotoListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultimo_dia);

        terremotoListView = (ListView) findViewById(R.id.lista_terremoto_view2);

        DescargaTerremotosAPI downloadTerremoto = new DescargaTerremotosAPI(); //Objeto que descarga los Json
        downloadTerremoto.delegate = this;

        try{
            downloadTerremoto.execute(new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson"));
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

                Intent intent = new Intent(UltimoDiaActivity.this, DetallesActivity.class);
                intent.putExtra(TERREMOTO_ACTUAL, terremotoActual);

                startActivity(intent);
            }
        });
    }

}
