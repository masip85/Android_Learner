package com.example.PantallaTactil;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class PantallaTactilActivity extends Activity implements OnTouchListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView entrada=(TextView) findViewById(R.id.TextViewEntrada);
        entrada.setOnTouchListener(this);
        
    }

	public boolean onTouch(View vista, MotionEvent evento) {
		TextView salida=(TextView) findViewById(R.id.TextViewSalida);
		String acciones[]={"ACTION_DOWN","ACTION_UP","ACTION_OUTSIDE","ACTION_POINTER_DOWN","ACTION_POINTER_UP"};
		int accion=evento.getAction();
		int codigoAccion=accion & MotionEvent.ACTION_MASK;
		salida.append(acciones[codigoAccion]);
		for (int i=0; i<evento.getPointerCount();i++){
			salida.append(" puntero:"+evento.getPointerId(i)+" x:"+evento.getX(i)+ " y:"+ evento.getY(i));
		}
		salida.append(" \n");
		return true;
	}
}