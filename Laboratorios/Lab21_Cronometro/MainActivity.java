package upv.avanzada.graficacion.cronometro;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Chronometer CR;
    int Counter;
    MediaPlayer mp;// = MediaPlayer.create(getApplicationContext(), R.raw.sonido);
    private long timeWhenStopped = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Counter = 0;
        mp = MediaPlayer.create(this, R.raw.sonido);

        CR = (Chronometer) findViewById(R.id.chronometer);
        CR.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                int elapsed =(int) ((SystemClock.elapsedRealtime() - chronometer.getBase())/1000);
                if (elapsed >= 20) {
                    mp.start();
                }
            }
        });
    }


    public void startButton(View view) {
        CR.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        CR.start();
    }

    public void stopButton(View view) {
        //((Chronometer) findViewById(R.id.chronometer)).stop();
        timeWhenStopped = CR.getBase() - SystemClock.elapsedRealtime();
        CR.stop();

        //mp.stop();
    }

    public void resetButton(View view) {
        //((Chronometer) findViewById(R.id.chronometer)).setBase(SystemClock.elapsedRealtime());
        //CR.setBase(SystemClock.elapsedRealtime());
        //CR.stop();

        CR.stop();
        timeWhenStopped=0; // resetear
        CR.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        //CR.start();

        //mp.stop();
    }
}
