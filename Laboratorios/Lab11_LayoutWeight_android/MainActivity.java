package upv.avanzada.graficacion.layoutweight_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
  private Button button1;
    private Button button2;
    private Button button3;
    private EditText text1;
    private EditText text2;
    private EditText text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        text1 = (EditText) findViewById(R.id.et1);
        text2 = (EditText) findViewById(R.id.et2);
        text3 = (EditText) findViewById(R.id.et3);

        text1.addTextChangedListener(new WeightChanger(button1));
        text2.addTextChangedListener(new WeightChanger(button2));
        text3.addTextChangedListener(new WeightChanger(button3));
    }

    private class WeightChanger implements TextWatcher {
        private Button button;

        WeightChanger(Button button) {
            this.button = button;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().equals("")) {
                return;
            }


        }

    }
}
