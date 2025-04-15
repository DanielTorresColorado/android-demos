package com.example.marco.canvas_mas_controles;

/* Fuente:
http://examples.javacodegeeks.com/android/core/graphics/canvas-graphics/android-canvas-example/
 */

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    private CanvasView customCanvas;
    private MySurfaceView SV1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
        SV1 = (MySurfaceView) findViewById(R.id.signature_canvas2);

    }

    public void clearCanvas(View v) {
        customCanvas.clearCanvas();


    }

    public void Funcion1(View v) {
        SV1.CambiarColor();


    }

    public void Funcion2(View v) {
        SV1.Limpiar();


    }

}
