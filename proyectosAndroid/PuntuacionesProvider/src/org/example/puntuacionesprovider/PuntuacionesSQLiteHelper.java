package org.example.puntuacionesprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PuntuacionesSQLiteHelper extends SQLiteOpenHelper {

	public PuntuacionesSQLiteHelper(Context context) {
		super(context, "puntuaciones", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE puntuaciones (_id INTEGER PRIMARY KEY AUTOINCREMENT, puntos INTENGER, nombre TEXT, fecha LONG)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}
	
}
