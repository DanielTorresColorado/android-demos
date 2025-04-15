package dispositivosmovilesmayago2018.upvictoria.com.proyectomenus;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.tv);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.red:
                // Set the text color to red
                mTextView.setTextColor(Color.RED);
                return true;
            case R.id.green:
                // Set the text color to green
                mTextView.setTextColor(Color.GREEN);
                return true;
            case R.id.blue:
                // Set the text color to blue
                mTextView.setTextColor(Color.BLUE);
                return true;
            case R.id.large_text:
                // Set the text size to large
                mTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50);
                return true;
            case R.id.medium_text:
                // Set the text size to medium
                mTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,35);
                return true;
            case R.id.small_text:
                // Set the text size to small
                mTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
