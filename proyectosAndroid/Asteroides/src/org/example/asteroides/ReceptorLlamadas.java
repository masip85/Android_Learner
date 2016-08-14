package org.example.asteroides;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReceptorLlamadas extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

    	// Arranca actividad desde el receptor de anuncios
//    	Intent i = new Intent(context, AcercaDe.class);
//    	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//    	context.startActivity(i);
    	
    	// Arranca tarea desde el receptor de anuncios
    	Intent i = new Intent(context, Asteroides.class);
    	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	context.startActivity(i);
    }
}
