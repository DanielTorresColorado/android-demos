package dispositivosmoviles.upvictoria.com.miercoles13;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    Spinner SP1;
    List<String> list;
    Button BT1;
    ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SP1 = (Spinner) findViewById(R.id.spinner1);
        BT1 = (Button) findViewById (R.id.button1);
        list = new ArrayList<String>();
        list.add("Programacion");
        list.add("Matematicas");
        list.add("Calculo");
        list.add("Algebra");
        list.add("Ing. Software");

        dataAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);

        SP1.setAdapter(dataAdapter);
        SP1.setOnItemSelectedListener(new CustomOnItemSelectedListener());



        BT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add("ElementoX");
                dataAdapter.notifyDataSetChanged();

            }
        });





    }


    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            Toast.makeText(parent.getContext(),
                    "Elemento Seleccionado : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }

}
