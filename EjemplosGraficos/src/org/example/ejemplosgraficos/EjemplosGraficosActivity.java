package org.example.ejemplosgraficos;


import android.app.Activity;
import android.os.Bundle;


public class EjemplosGraficosActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new EjemploView(this, null));
    }
    

}