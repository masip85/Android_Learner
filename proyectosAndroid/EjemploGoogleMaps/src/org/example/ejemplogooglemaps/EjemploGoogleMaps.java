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

public class EjemploGoogleMaps extends MapActivity {

	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;

	public void onCreate(Bundle bundle) {

		super.onCreate(bundle);
		setContentView(R.layout.main);

		mapView = (MapView) findViewById(R.id.mapa);
		mapView.setBuiltInZoomControls(true); // Activa controles zoom
		mapView.setSatellite(true); // Activa vista satélite
		mapView.setStreetView(false); // Desactiva StreetView
		mapView.setTraffic(false); // Desactiva información de tráfico
		mapController = mapView.getController();
		mapController.setZoom(14); // Zoon 1 ver todo el mundo
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, new GeoUpdateHandler());
	}

	@Override
	protected boolean isRouteDisplayed() {

		return false;
	}

	public class GeoUpdateHandler implements LocationListener {

		public void onLocationChanged(Location location) {

			int lat = (int) (location.getLatitude() * 1E6);

			int lng = (int) (location.getLongitude() * 1E6);

			GeoPoint point = new GeoPoint(lat, lng);

			mapController.setCenter(point);

		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}

	}


	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}