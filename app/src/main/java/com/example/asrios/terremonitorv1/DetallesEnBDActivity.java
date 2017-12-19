package com.example.asrios.terremonitorv1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 * Clase DetallesEnBDActivity
 *
 * Clase para manejar la información de cada terremoto para ver la información detallada de cada terremoto
 * guardado en la base de datos.
 * Tiene un botón para ver el mapa y otro para eliminarlo en la base de datos
 * @author Alexandro Sánchez Rios
 * @version 1.0
 * </pre>
 */

public class DetallesEnBDActivity extends AppCompatActivity {

    public final static String TERREMOTO_ACTUAL = "terremoto_actual";
    Terremoto terremoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_en_bd);

        Bundle extras = getIntent().getExtras();
        terremoto = extras.getParcelable(UltimaHoraActivity.TERREMOTO_ACTUAL);

        if (terremoto != null) {
            TextView magnitudTxt = (TextView) findViewById(R.id.eq_detail_magnitude);
            TextView longitudTxt = (TextView) findViewById(R.id.eq_detail_longitude);
            TextView latitudTxt = (TextView) findViewById(R.id.eq_detail_latitude);
            TextView lugarTxt = (TextView) findViewById(R.id.eq_detail_place);
            TextView dateTimeTxt = (TextView) findViewById(R.id.eq_detail_date_time);

            magnitudTxt.setText(String.valueOf(terremoto.getMagnitud()));
            longitudTxt.setText(String.valueOf(terremoto.getLongitud()));
            latitudTxt.setText(String.valueOf(terremoto.getLatitud()));
            lugarTxt.setText(String.valueOf(terremoto.getLugar()));
            dateTimeTxt.setText(String.valueOf(getStringDateFromTimestamp(terremoto.getDateTime())));
        }
    }

    private String getStringDateFromTimestamp(long timestamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/yyyy - H:mm:ss", Locale.getDefault());

        Date date = new Date (timestamp);
        return simpleDateFormat.format(date);
    }


    public void borrarOnClick (View v){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();


        int cant = bd.delete("terremoto", "timestamp="+terremoto.getDateTime(),null);
        bd.close();

        if (cant ==1)
            Toast.makeText(this,"Se borró el terremoto de la base de datos", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"ERROR: no se pudo borrar el terremoto", Toast.LENGTH_LONG).show();

    }

    public void verMapa (View v){
        Intent intent = new Intent(DetallesEnBDActivity.this, MapActivity.class);
        Bundle b = new Bundle();
        b.putString("longitud", terremoto.getLongitud());
        b.putString("latitud", terremoto.getLatitud());
        intent.putExtras(b);
        startActivity(intent);

    }




}
