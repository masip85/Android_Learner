package org.example.localizacion;

import java.util.List;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.TextView;

public class LocalizacionActivity extends Activity implements LocationListener{
	private static final String[] A={ "n/d","preciso","impreciso"};
	private static final String[] P={ "n/d","bajo","medio","alto"};
	private static final String[] E={ "fuera de servicio","temporalmente no disponible","disponible"};
	private LocationManager manejador;
	private TextView salida;
	private String proveedor;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        salida=(TextView) findViewById(R.id.salida);
        manejador=(LocationManager)getSystemService(LOCATION_SERVICE);
        
        log("Proveedores de localizacion: \n");
        muestraProveedores();
        
        Criteria criteria=new Criteria();
        proveedor=manejador.getBestProvider(criteria, true);
        log("Mejor proveedor: "+proveedor+"\n");
        
        log("Comenzamos con la última localización conocida:");
        Location localizacion=manejador.getLastKnownLocation(proveedor);
        muestraLocalizacion(localizacion);
    }
    
    
    protected void onResume(){
    	super.onResume();
    	manejador.requestLocationUpdates(proveedor, 10000, 1, this);
    }
    
    protected void onPause(){
    	super.onPause();
    	manejador.removeUpdates(this);    
    	}
    
    public void onLocationChanged(Location location){
    	log("Nueva localizacion: ");
    	muestraLocalizacion(location);
    }
    
    public void onProviderDisabled(String proveedor){
    	log("Proveedor deshabilitado: "+ proveedor+"\n");
    }
    
    public void onProviderEnabled(String proveedor){
    	log("Proveedor habilitado: "+ proveedor+"\n");
    }
    public void onStatusChanged(String proveedor,int estado,Bundle extras){
    	log("Cambia estado proveedor: "+proveedor+", estado="+E[Math.max(0,estado)]+", extras="+extras+"\n");
    }
    private void log(String cadena){
    	salida.append(cadena+"\n");
    }
    private void muestraLocalizacion(Location localizacion){
    	if(localizacion==null)
    		log("Localizacion desconocida\n");
    	else
    		log(localizacion.toString()+"\n");
    	log("hola ghsdad");
    }
    private void muestraProveedores(){
    	List<String>proveedores=manejador.getAllProviders();
    	for(String proveedor : proveedores){
    		muestraProveedor(proveedor);
    	}
    }
    private void muestraProveedor(String proveedor) {
        LocationProvider info = manejador.getProvider(proveedor);
        log("LocationProvider[ "+"getName=" + info.getName()+
        ", isProviderEnabled=" + manejador.isProviderEnabled(proveedor)+ 
        ", getAccuracy=" + A[Math.max(0, info.getAccuracy())]+
        ", getPowerRequirement=" +
        P[Math.max(0, info.getPowerRequirement())]+
        ", hasMonetaryCost=" + info.hasMonetaryCost()+
        ", requiresCell=" + info.requiresCell()+
        ", requiresNetwork=" + info.requiresNetwork()+
        ", requiresSatellite=" + info.requiresSatellite()+
        ", supportsAltitude=" + info.supportsAltitude()+
        ", supportsBearing=" + info.supportsBearing()+
        ", supportsSpeed=" + info.supportsSpeed()+" ]\n");
       }
}