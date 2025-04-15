package tuchingadamadre.maganda.nuno.marco.ciclodevida_androidactivity;
/* Fuente:
http://www.tutorialspoint.com/android/android_acitivities.htm


Si hay una diferencia entre el boton BACK y el botón HOME
-- Llamada
-- Mensaje 
 */



import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toast.makeText(getApplicationContext(), "Mensaje a Desplegar", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), "Mensaje a Desplegar", Toast.LENGTH_LONG).show();

        Toast.makeText(getApplicationContext(), "Ejecución de OnCreate", Toast.LENGTH_SHORT).show();

    }

    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "Ejecución de OnStart", Toast.LENGTH_SHORT).show();
    }


    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "Ejecución de OnResume", Toast.LENGTH_SHORT).show();
    }

    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "Ejecución de OnPause", Toast.LENGTH_SHORT).show();
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "Ejecución de OnStop", Toast.LENGTH_SHORT).show();
    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Ejecución de OnDestroy", Toast.LENGTH_SHORT).show();
    }


}
