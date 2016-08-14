package org.example.tablayout;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class TabLayoutActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TabHost tabHost = getTabHost();
        
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Título 1", null).setContent(R.id.tab1Layout));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Título 2", null).setContent(R.id.tab2Layout));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Título 3", null).setContent(R.id.tab3Layout));
        
        
    }
}