package com.example.marco.proyecto01;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button B1;
    TextView TV1;
    CountDownTimer CDT1;
    EditText ET1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        B1 = (Button) findViewById(R.id.button1);
        TV1 = (TextView) findViewById(R.id.textView1);
        ET1 = (EditText) findViewById(R.id.editText1);
        ET1.setText("18");

        //CrearCDT(15000);





        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Valor = Integer.parseInt(ET1.getText().toString())*1000;
                CrearCDT (Valor);
                B1.setEnabled(false);
                CDT1.start();

            }
        });
    }

    void CrearCDT (int ValorCuenta) {
        CDT1 = new CountDownTimer(ValorCuenta,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TV1.setText("Segundos restantes: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                TV1.setText("Terminado!");
                B1.setEnabled(true);
            }
        };
    }    
}
