package com.example.marco.gson_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/* Para poder agregar este import es necesario agregar la siguiente linea:
compile 'com.google.code.gson:gson:2.3.1'
Al archivo build.graddle (Module:app)
Inmediantamente despues de la linea
compile 'com.android.support:appcompat-v7:25.1.0'
Resincronizar proyecto (CON CONEXION A INTERNET!!)
 */
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final Context context = this;

    ListView LV1;

    ArrayList<EstudiantesGueros> ListaEG;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    ArrayAdapter<EstudiantesGueros> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LV1 = (ListView) findViewById(R.id.listview1);


    }

    @Override
    public void onResume () {
        super.onResume();
        ListaEG = new ArrayList<EstudiantesGueros>();

        sharedPrefs = context.getSharedPreferences("MarcoNunoGSON5", Context.MODE_PRIVATE);
        editor = sharedPrefs .edit();

        String connectionsJSONString = sharedPrefs.getString("ListaGueros", null);
        if (connectionsJSONString!=null) {
            Type type = new TypeToken<ArrayList<EstudiantesGueros>>() {}.getType();
            ListaEG = new Gson().fromJson(connectionsJSONString, type);
            Toast.makeText(getApplicationContext(), "Elementos en la lista: " + ListaEG.size(), Toast.LENGTH_SHORT).show();
            adapter = new ArrayAdapter<EstudiantesGueros>(getApplication(),android.R.layout.simple_list_item_1, ListaEG);
            LV1.setAdapter(adapter);
        }
        else
            Toast.makeText(getApplicationContext(), "Lista NULA!!: " , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        EstudiantesGueros EG1 = new EstudiantesGueros(18,"Nuño","Maganda","Toromaxito",2,true,true,10.5,5.5);
        ListaEG.add(EG1);

        String connectionsJSONString = new Gson().toJson(ListaEG);
        editor.putString("ListaGueros", connectionsJSONString);
        editor.apply();

        //Toast.makeText(getApplicationContext(),"Elementos en la lista: "+ListaEG.size()+"\n"+connectionsJSONString,Toast.LENGTH_SHORT).show();
    }
}
