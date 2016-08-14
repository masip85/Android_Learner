package org.example.asteroides;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.jar.Attributes;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

public class AlmacenPuntuacionesProvider implements AlmacenPuntuaciones {

	private Activity activity;
	
	public AlmacenPuntuacionesProvider(Activity activity) {
		this.activity = activity;
	}

	public void guardarPuntuacion(int puntos, String nombre, long fecha) {
		Uri uri = Uri.parse("content://org.example.puntuacionesprovider/puntuaciones");
		ContentValues valores = new ContentValues();
		valores.put("nombre" , nombre);
		valores.put("puntos", puntos);
		valores.put("fecha", fecha);
		try {
			activity.getContentResolver().insert(uri, valores);
		} catch (Exception e) {
			Toast.makeText(activity, "Verificar que esta instalado org.example.puntuacionesprovider", Toast.LENGTH_SHORT).show();
			Log.e("Asteroides", "Error: " + e.toString(), e);
		}
	}

	public Vector<String> listaPuntuaciones(int cantidad) {

		Vector<String> result = new Vector<String>();
		Uri uri = Uri.parse("content://org.example.puntuacionesprovider/puntuaciones");
		Cursor cursor = activity.managedQuery(uri, null, null, null, "fecha DESC");
		
		if(cursor != null) {
			while(cursor.moveToNext()){
				String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
				int puntos = cursor.getInt(cursor.getColumnIndex("puntos"));
				result.add(puntos + " " + nombre);
			}
			cursor.close();
		}
		return result;
	}
}
