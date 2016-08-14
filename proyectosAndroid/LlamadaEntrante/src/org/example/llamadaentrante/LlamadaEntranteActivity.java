package org.example.llamadaentrante;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class LlamadaEntranteActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llamada_entrante);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_llamada_entrante, menu);
        return true;
    }
}
