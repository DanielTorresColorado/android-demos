package upv.avanzada.graficacion.listview_multichoice;

/*
Fuente:
https://www.vogella.com/tutorials/AndroidListView/article.html
http://techin-android.blogspot.com/2011/05/list-view-with-multi-select-items.html
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;
    Button Ok;
    String array[]=new String[]{"a","b","c","d","e","f","g","h","i"};
    String temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.lv1);
        textView = (TextView)findViewById(R.id.tv2);
        Ok = (Button)findViewById(R.id.bt1  );

        /**
         * Setting ListView adapter using AttayAdapter.
         */

        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,array));
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Estatus = (((CheckedTextView) view).isChecked() ? "Seleccionado" : "Des-seleccionado");
                String Elemento = ((CheckedTextView) view).getText().toString();
                Toast.makeText(getApplicationContext(),"Elemento Clickeado"+Elemento+"["+position+"] -("+Estatus+")",Toast.LENGTH_SHORT).show();
            }
        });

        Ok.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                System.out.println("check Items:"+listView.getCheckItemIds().length);
                getSelectedItems(listView.getCheckItemIds().length);
            }
        });

    }

    /**
     * Method to get Selected Items from the ListView.
     * @param length - Length of the number of CheckBox that are selected.
     */

    private void getSelectedItems(int length){
        temp="Selected Elements: \n";
        for(int i=0;i<length;i++){
            temp+=array[(int) listView.getCheckItemIds()[i]]+" \n";
        }
        textView.setText(temp);
    }
}
