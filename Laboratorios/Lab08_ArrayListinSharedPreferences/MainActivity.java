package com.example.marco.custom_alertdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    final Context context = this;
    Button B1, B2;

    EditText ET1;
    ListView LV1;

    ArrayList<String> ListaConceptos;
    ArrayAdapter<String> adapter;

    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LV1 = (ListView) findViewById(R.id.listview1);
        B1 = (Button) findViewById(R.id.button);
        B2 = (Button) findViewById(R.id.button2);
        ET1 = (EditText) findViewById(R.id.editText);

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListaConceptos.add(ET1.getText().toString());
                adapter.notifyDataSetChanged();
                ET1.setText("");
            }
        });

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Eliminar todos los conceptos
                ListaConceptos = new ArrayList<String>();
                adapter = new ArrayAdapter<String>(getApplication(),android.R.layout.simple_list_item_1, ListaConceptos);
                LV1.setAdapter(adapter);
                ET1.setText("");

            }
        });
    }

    @Override
    public void onResume () {
        super.onResume();
        ListaConceptos = new ArrayList<String>();

        sharedPrefs = context.getSharedPreferences("MarcoNuno", Context.MODE_PRIVATE);
        editor = sharedPrefs .edit();

        String serialized = sharedPrefs.getString("ListaConceptos", null);
        if (serialized!=null) {
            Toast.makeText(getApplicationContext(), serialized, Toast.LENGTH_SHORT).show();
            ListaConceptos = new ArrayList(Arrays.asList(serialized.split(",")));
        }
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ListaConceptos);
        LV1.setAdapter(adapter);

    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first


        String Listota="";
        if (ListaConceptos.size()>0) Listota = TextUtils.join(",", ListaConceptos);
        else Listota = null;


        editor.putString("ListaConceptos", Listota);
        editor.apply();

        Toast.makeText(getApplicationContext(),Listota,Toast.LENGTH_SHORT).show();
        //editor.commit();
    }



}
