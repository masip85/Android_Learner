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
        
        TextView entrada = (TextView) this.findViewById(R.id.TextViewEntrada);
        entrada.setOnTouchListener(this);
    }

	public boolean onTouch(View vista, MotionEvent evento) {

		TextView salida = (TextView) this.findViewById(R.id.TextViewSalida);
		salida.append(evento.toString()+"\n");
		
		return true;
	}
}