package org.example.serviciocliente;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ServicioRemotoActivity extends Activity {
    /** Called when the activity is first created. */
	private IServicioMusica servicio;
	
	private ServiceConnection conexion=new ServiceConnection(){
		public void onServiceConnected(ComponentName className,IBinder iservicio){
			servicio=IServicioMusica.Stub.asInterface(iservicio);
		}
		
		public void onServiceDisconnected(ComponentName className){
			servicio=null;
			Toast.makeText(ServicioRemotoActivity.this, "Se ha perdido la conexiion con el Servicio", Toast.LENGTH_SHORT).show();
		}
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button boton_conectar=(Button) findViewById(R.id.boton_conectar);        
		boton_conectar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				ServicioRemotoActivity.this.bindService(new Intent(
						ServicioRemotoActivity.this, ServicioRemoto.class),
						conexion, Context.BIND_AUTO_CREATE);
			}
		});
		
		Button boton_reproducir=(Button) findViewById(R.id.boton_reproducir);        
		boton_reproducir.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				try {
					servicio.reproduce("titulo");
				} catch (Exception e) {
					Toast.makeText(ServicioRemotoActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		Button boton_avanzar=(Button) findViewById(R.id.boton_avanzar);        
		boton_avanzar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				try {
					servicio.setPosicion(servicio.getPosicion()+1000);
				} catch (Exception e) {
					Toast.makeText(ServicioRemotoActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		Button boton_desconectar=(Button) findViewById(R.id.boton_desconectar);        
		boton_desconectar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				try {
					ServicioRemotoActivity.this.unbindService(conexion);
				} catch (Exception e) {
					Toast.makeText(ServicioRemotoActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
				}
				servicio=null;
			}
		});
    }
}