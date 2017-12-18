package com.example.asrios.terremonitorv1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DataBaseConsultActivity extends AppCompatActivity {
    public final static String TERREMOTO_ACTUAL = "terremoto_actual";
    ListView terremotoListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base_consult);

        terremotoListView = (ListView) findViewById(R.id.data_base_view);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase database = admin.getReadableDatabase();

        Cursor cursor = database.query("terremoto",null,null,null,null,null,null);

        ArrayList<Terremoto> terremotosLista = new ArrayList<>();
        while (cursor.moveToNext()){
            Long dateTime = cursor.getLong(5);
            Double magnitud = cursor.getDouble(1);
            String lugar = cursor.getString(2);
            String longitud = cursor.getString(3);
            String latitud = cursor.getString(4);

            terremotosLista.add(new Terremoto(dateTime, magnitud, lugar, longitud, latitud));
        }
        cursor.close();

        llenarListaTerremotos (terremotosLista);
    }


    private void llenarListaTerremotos (ArrayList<Terremoto> terremotosLista){

        final AdaptadorTerremoto terremotoAdapter = new AdaptadorTerremoto(this, R.layout.lista_terremoto_elemento, terremotosLista);

        terremotoListView.setAdapter(terremotoAdapter);


        terremotoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Terremoto terremotoActual = terremotoAdapter.getItem(position);

                Intent intent = new Intent(DataBaseConsultActivity.this, DetallesEnBDActivity.class);
                intent.putExtra(TERREMOTO_ACTUAL, terremotoActual);

                startActivity(intent);
            }
        });
    }


    public void deleteAllDB (View v){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();


        int cant = bd.delete("terremoto", null,null);
        bd.close();

        if (cant > 1)
            Toast.makeText(this,"se borraron todos los terremotos", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"ERROR: no se pudieron borrar los terremotos", Toast.LENGTH_LONG).show();

    }

}
