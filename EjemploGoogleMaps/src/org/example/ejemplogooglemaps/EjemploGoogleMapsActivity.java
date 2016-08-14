package org.example.ejemplogooglemaps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class EjemploGoogleMapsActivity extends MapActivity implements
		LocationListener {

	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.main);
		mapView = (MapView) findViewById(R.id.mapa);
		mapView.setBuiltInZoomControls(true); // Activa controles zoom
		mapView.setSatellite(true); // Activa vista sat�lite
		mapView.setStreetView(false); // Desactiva StreetView
		mapView.setTraffic(false); // Desactiva informaci�n de tr�fico
		mapController = mapView.getController();
		mapController.setZoom(14); // Zoon 1 ver todo el mundo
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 1, this);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void onProviderDisabled(String provider) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}
}
