package org.example.servicioremoto;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ActividadPrincipal extends Activity {

	private IServicioMusica servicio;
	
	private ServiceConnection conexion = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder iservicio) {
			servicio = IServicioMusica.Stub.asInterface(iservicio);
			Toast.makeText(ActividadPrincipal.this, "Conectado a Servicio", Toast.LENGTH_SHORT).show();
		}
		public void onServiceDisconnected(ComponentName className) {
			servicio = null;
			Toast.makeText(ActividadPrincipal.this, "Se ha perdido la conexion con el servidor", Toast.LENGTH_SHORT).show();
		}
	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);
        
        Button botonConectar = (Button) this.findViewById(R.id.boton_conectar);
        botonConectar.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		ActividadPrincipal.this.bindService(new Intent(ActividadPrincipal.this, ServicioRemoto.class), conexion, Context.BIND_AUTO_CREATE);
        	}
        });
        
		Button botonReproducir = (Button) this.findViewById(R.id.boton_reproducir);
		botonReproducir.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					servicio.reproduce("titulo");
				} catch (Exception e) {
					Toast.makeText(ActividadPrincipal.this, e.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		Button botonAvanzar = (Button) this.findViewById(R.id.boton_avanzar);
		botonAvanzar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					servicio.setPosicion(servicio.getPosicion()+1000);
				} catch (Exception e) {
					Toast.makeText(ActividadPrincipal.this, e.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		Button botonDetener = (Button) this.findViewById(R.id.boton_desconectar);
		botonDetener.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					ActividadPrincipal.this.unbindService(conexion);
				} catch (Exception e) {
					Toast.makeText(ActividadPrincipal.this, e.toString(), Toast.LENGTH_SHORT).show();
				}
				servicio = null;
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_actividad_principal, menu);
        return true;
    }
}
