package tuchingadamadre.maganda.nuno.marco.pestanasmultiples;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Importante: Esto va antes de instanciar controles dentro de cada pestaña

        // Agregar las pestañas---
        Resources res = getResources();
        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("PASES LISTA");

        TabHost.TabSpec spec2 = tabHost.newTabSpec("");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("Grupos");

        TabHost.TabSpec spec3 = tabHost.newTabSpec("");
        spec3.setContent(R.id.tab3);
        spec3.setIndicator("Alumnos");
        //spec3.setIndicator("",getResources().getDrawable(R.mipmap.ic_launcher));

        TabHost.TabSpec spec4 = tabHost.newTabSpec("");
        spec4.setContent(R.id.tab4);
        spec4.setIndicator("Estadisticas");

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);
        tabHost.addTab(spec4);

        // Otros Recursos (TextView, Buttons, ListView, EditText, etx)

    }
}
