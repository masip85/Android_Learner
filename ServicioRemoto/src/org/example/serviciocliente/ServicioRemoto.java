package org.example.serviciocliente;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;

public class ServicioRemoto extends Service{
	
	MediaPlayer reproductor;
	
	public void onCreate(){
		super.onCreate();
		reproductor=MediaPlayer.create(ServicioRemoto.this, R.raw.audio);
	}
	
	
		
		private final IServicioMusica.Stub binder=new IServicioMusica.Stub() {
			
			public void setPosicion(int ms) throws RemoteException {
				reproductor.seekTo(ms);
				
			}
			
			public String reproduce(String mensaje) throws RemoteException {
				reproductor.start();
				return mensaje;
			}
			
			public int getPosicion() throws RemoteException {
				return reproductor.getCurrentPosition();
			}
		};
	
		public IBinder onBind(Intent intent){
			return this.binder;
		}

}
