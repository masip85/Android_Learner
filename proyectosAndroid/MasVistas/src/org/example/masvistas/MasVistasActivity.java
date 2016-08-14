package org.example.masvistas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MasVistasActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    public void sePulsa(View view) {
    	Toast.makeText(this, "Pulsado", Toast.LENGTH_SHORT).show();
    }
    
}