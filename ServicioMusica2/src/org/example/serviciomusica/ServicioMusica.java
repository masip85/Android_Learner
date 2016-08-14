package org.example.serviciomusica;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class ServicioMusica extends Service {
	MediaPlayer reproductor;
	private NotificationManager nm;
	private static final int ID_NOTIFICATION_CREAR=1;
	Notification notificacion;
	
	public void onCreate(){
		Toast.makeText(this,"Servicio creado",Toast.LENGTH_SHORT).show();
		reproductor=MediaPlayer.create(this, R.raw.audio);
		nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	}
	
	public int onStartCommand(Intent intencion,int flags,int idArranque){
		
		
		 notificacion = new Notification(R.drawable.ic_launcher,"Creando Servicio de Musica",System.currentTimeMillis());
		PendingIntent intencionPendiente=PendingIntent.getActivity(this, 0, new Intent(this,ServicioMusicaActivity.class), 0);
		notificacion.setLatestEventInfo(this,"Reproduciendo música","información adicional",intencionPendiente);
		nm.notify(ID_NOTIFICATION_CREAR,notificacion);
		Toast.makeText(this, "Servicio arancado"+ idArranque,Toast.LENGTH_SHORT).show();
		reproductor.start();
		
		return START_STICKY;
	}
	
//	public void onStart(Intent intencion,int flags,int idArranque){
//	Toast.makeText(this, "Servicio arancado"+ idArranque,Toast.LENGTH_SHORT).show();
//	reproductor.start();
//}

	public void onDestroy(){
		Toast.makeText(this, "Servicio detenido", Toast.LENGTH_SHORT);
		reproductor.stop();
		nm.cancel(ID_NOTIFICATION_CREAR);
		
	}
	public IBinder onBind(Intent intencion){
		return null;
	}
}
