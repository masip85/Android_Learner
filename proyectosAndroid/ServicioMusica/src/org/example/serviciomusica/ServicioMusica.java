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
	Notification notificacion;
	private NotificationManager nm;
	private static final int ID_NOTIFICACION_CREAR = 1;

	@Override
	public void onCreate() {
		Toast.makeText(this, "Servicio creado", Toast.LENGTH_LONG).show();
		reproductor = MediaPlayer.create(this, R.raw.audio);
	}

	@Override
	public int onStartCommand(Intent intencion, int flags, int idArranque) {
		Toast.makeText(this, "Servicio arrancado" + idArranque,
				Toast.LENGTH_LONG).show();
		reproductor.start();

		notificacion = new Notification(R.drawable.ic_launcher,
				"Creando Servicio de Musica", System.currentTimeMillis());
		
		PendingIntent intencionPendiente = PendingIntent.getActivity(this, 0,
				new Intent(this, ActividadPrincipal.class), 0);
		notificacion.setLatestEventInfo(this, "Reproduciendo musica",
				"Informacion adicional", intencionPendiente);

		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.notify(ID_NOTIFICACION_CREAR, notificacion);

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		nm.cancel(ID_NOTIFICACION_CREAR);
		Toast.makeText(this, "Servicio detenido", Toast.LENGTH_LONG).show();
		reproductor.stop();
	}

	@Override
	public IBinder onBind(Intent intencion) {
		return null;
	}
}