package org.example.asteroides;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

public class AlmacenPuntuacionesSerWeb implements AlmacenPuntuaciones {

	private Activity activity;
	
	public AlmacenPuntuacionesSerWeb(Activity activity) {
		this.activity = activity;
	}

	public void guardarPuntuacion(int puntos, String nombre, long fecha) {
		try {
			URL url = new URL("http://158.42.146.127:8080/PuntuacionesServicioWeb/services/PuntuacionesServicioWeb.PuntuacionesServicioWebHttpEndpoint/lista");
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			
			conexion.setRequestMethod("POST");
			conexion.setDoOutput(true);
			OutputStreamWriter sal = new OutputStreamWriter(conexion.getOutputStream());
			
			sal.write("maximo=");
			sal.write(URLEncoder.encode(String.valueOf(puntos), "UTF-8"));
			sal.write("&nombre=");
			sal.write(URLEncoder.encode(nombre, "UTF-8"));
			sal.write("&fecha=");
			sal.write(URLEncoder.encode(String.valueOf(fecha), "UTF-8"));
			sal.flush();
			if(conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
				SAXParserFactory fabrica = SAXParserFactory.newInstance();
				SAXParser parser = fabrica.newSAXParser();
				XMLReader lector = parser.getXMLReader();
				ManejadorSerWeb manejadorXML = new ManejadorSerWeb();
				lector.setContentHandler(manejadorXML);
				lector.parse(new InputSource(conexion.getInputStream()));
				if(manejadorXML.getLista().size() != 1 || !manejadorXML.getLista().get(0).equals("OK")) {
					Log.e("Asteroides", "Error en respuesta servicio Web nueva");
				}
			} else {
				Log.e("Asteroides", conexion.getResponseMessage());
			}
			conexion.disconnect();
		} catch(Exception e) {
			Log.e("Asteroides", e.getMessage(), e);
		}
	}

	public Vector<String> listaPuntuaciones(int cantidad) {
		try {
			URL url = new URL("http://158.42.146.127:8080/PuntuacionesServicioWeb/services/PuntuacionesServicioWeb.PuntuacionesServicioWebHttpEndpoint/lista");
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			
			conexion.setRequestMethod("POST");
			conexion.setDoOutput(true);
			OutputStreamWriter sal = new OutputStreamWriter(conexion.getOutputStream());
			
			sal.write("maximo=");
			sal.write(URLEncoder.encode(String.valueOf(cantidad), "UTF-8"));
			sal.flush();
			
			if(conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
				SAXParserFactory fabrica = SAXParserFactory.newInstance();
				SAXParser parser = fabrica.newSAXParser();
				XMLReader lector = parser.getXMLReader();
				ManejadorSerWeb manejadorXML = new ManejadorSerWeb();
				lector.setContentHandler(manejadorXML);
				lector.parse(new InputSource(conexion.getInputStream()));
				return manejadorXML.getLista();
			} else {
				Log.e("Asteroides", conexion.getResponseMessage());
				return null;
			}
		} catch (Exception e) {
			Log.e("Asteroides", e.getMessage());
			return null;
		}
	}
	
	
	class ManejadorSerWeb extends DefaultHandler {
		private Vector<String> lista;
		private StringBuilder cadena;
		
		public Vector<String> getLista() {
			return lista;
		}
		
		@Override
		public void startDocument() throws SAXException {
			cadena = new StringBuilder();
			lista = new Vector<String>();
		}
		
		@Override
		public void characters(char ch[], int comienzo, int longitud) {
			cadena.append(ch, comienzo, longitud);
		}
		
		@Override
		public void endElement (String uri, String nombreLocal, String nombreCualif) throws SAXException {
			if (nombreLocal.equals("return")) {
				try{
					lista.add(URLDecoder.decode(cadena.toString(), "UTF-8"));
				} catch(UnsupportedEncodingException e) {
					Log.e("Asteroides", e.getMessage(), e);
				}
			}
			cadena.setLength(0);
		}
	}
	
	
	
}
