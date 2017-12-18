package com.example.asrios.terremonitorv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void ultimaHora (View v){
        Intent intent = new Intent(MainActivity.this, UltimaHoraActivity.class);
        Bundle b = new Bundle();
        intent.putExtras(b);
        startActivity(intent);
    }


    public void ultimoDia (View v){
        Intent intent = new Intent(MainActivity.this, UltimoDiaActivity.class);
        Bundle b = new Bundle();
        intent.putExtras(b);
        startActivity(intent);
    }

    public void ultimosSiete (View v){
        Intent intent = new Intent(MainActivity.this, UltimosSieteActivity.class);
        Bundle b = new Bundle();
        intent.putExtras(b);
        startActivity(intent);
    }

    public void ultimosTreinta(View v){
        Intent intent = new Intent(MainActivity.this, UltimosTreintaActivity.class);
        Bundle b = new Bundle();
        intent.putExtras(b);
        startActivity(intent);
    }


    public void apiWebView (View v){
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        Bundle b = new Bundle();
        intent.putExtras(b);
        startActivity(intent);
    }

    public void verGuardados (View v){
        Intent intent = new Intent(MainActivity.this, DataBaseConsultActivity.class);
        Bundle b = new Bundle();
        intent.putExtras(b);
        startActivity(intent);
    }
}
