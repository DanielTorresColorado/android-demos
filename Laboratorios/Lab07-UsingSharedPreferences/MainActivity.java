package upv.avanzada.graficacion.usingsharedpreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
Nota con respecto a Edit y Commit
I noticed, it is important to write difference between commit() and apply() here as well.

commit() return true if value saved successfully otherwise false. It save values to SharedPreferences synchronously.

apply() was added in 2.3 and doesn't return any value either on success or failure.
It saves values to SharedPreferences immediately but starts an asynchronous commit.

 */

public class MainActivity extends AppCompatActivity {
    EditText ed1,ed2,ed3;

   public static final String MyPREFERENCES = "MyPrefs" ;
   public static final String Name = "nameKey";
   public static final String Phone = "phoneKey";
   public static final String Email = "emailKey";

   SharedPreferences sharedpreferences;
   SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      ed1=(EditText)findViewById(R.id.editText);
      ed2=(EditText)findViewById(R.id.editText2);
      ed3=(EditText)findViewById(R.id.editText3);

      sharedpreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(this);



    }

/*
    // Cuando la aplicacion deja el primer plano se ejecuta..
     @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
            editor = sharedpreferences.edit();

            String n  = ed1.getText().toString();
            String ph  = ed2.getText().toString();
            String e  = ed3.getText().toString();

            editor.putString(Name, n);
            editor.putString(Phone, ph);
            editor.putString(Email, e);
            //editor.commit();
            editor.apply();

    }


    // Cuando la aplicación va a primer plano (despues de OnCreate) se ejecuta..
    @Override
    public void onResume () {
        super.onResume();
        sharedpreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String Cadena = sharedpreferences.getString(Name, "");
        if (!Cadena.equalsIgnoreCase("")) {
            ed1.setText(Cadena);
        }
        Cadena = sharedpreferences.getString(Phone, "");
        if (!Cadena.equalsIgnoreCase("")) {
            ed2.setText(Cadena);
        }
        Cadena = sharedpreferences.getString(Email, "");
        if (!Cadena.equalsIgnoreCase("")) {
            ed3.setText(Cadena);
        }

        //Toast.makeText(MainActivity.this,"Cargado!!",Toast.LENGTH_LONG).show();


    }
    */


}
