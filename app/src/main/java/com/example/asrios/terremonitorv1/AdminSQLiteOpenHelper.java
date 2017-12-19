package com.example.asrios.terremonitorv1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * <pre>
 * Clase AdminSQLiteOpenHelper
 *
 * Clase para construir una base de datos SQLite
 * Hereda la clase SQLiteOpenHelper
 * @author Francisco Altamirano
 * @version 1.0
 * </pre>
 */

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE terremoto (id INTEGER PRIMARY KEY AUTOINCREMENT, magnitud REAL NOT NULL, lugar TEXT NOT NULL, longitud TEXT NOT NULL, latitud TEXT NOT NULL, timestamp TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS earthquake");
        onCreate(db);
    }
}
