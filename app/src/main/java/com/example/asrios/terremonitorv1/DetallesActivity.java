package com.example.asrios.terremonitorv1;

import android.content.ContentValues;
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
 * Clase DetallesActivity
 *
 * Clase para manejar la información de cada terremoto para ver la información detallada de cada terremoto.
 * Tiene un botón para ver el mapa y otro para guardarlo en la base de datos
 * @author Alexandro Sánchez Rios
 * @version 1.0
 * </pre>
 */

public class DetallesActivity extends AppCompatActivity {

    public final static String TERREMOTO_ACTUAL = "terremoto_actual";
    Terremoto terremoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

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

    public void altaOnClick (View v){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("magnitud", terremoto.getMagnitud());
        registro.put("lugar",terremoto.getLugar());
        registro.put("longitud",terremoto.getLongitud());
        registro.put("latitud",terremoto.getLatitud());
        registro.put("timestamp",terremoto.getDateTime());

        long status = database.insert("terremoto",null,registro);

        if (status<0){
            Toast.makeText(this,"No se pudo guardar el Terremoto", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Se ha guardado el terremoto en la base de datos", Toast.LENGTH_LONG).show();
        }
    }


    public void verMapa (View v){
        Intent intent = new Intent(DetallesActivity.this, MapActivity.class);
        Bundle b = new Bundle();
        b.putString("longitud", terremoto.getLongitud());
        b.putString("latitud", terremoto.getLatitud());
        intent.putExtras(b);
        startActivity(intent);
    }


}
