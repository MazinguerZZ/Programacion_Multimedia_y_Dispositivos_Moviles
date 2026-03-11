package com.pmm.puzlebotones;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivityActivities extends AppCompatActivity {

    private Button[][] botones = new Button[3][3];
    private int colorOriginal;
    private int colorSecundario;

    private int contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) { // (f)
            contador = 0; // (c)
        }
        else contador = (savedInstanceState.getInt("VALOR_CONTADOR"));

        colorOriginal= getColor(R.color.LTGRAY);
        colorSecundario = getColor(R.color.BLUE);

        // Contenedor principal
        GridLayout layout = new GridLayout(this);
        layout.setRowCount(4); // 3 filas para botones + 1 para reiniciar
        layout.setColumnCount(3);

        // Crear botones 3x3
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                Button b = new Button(this);
                b.setBackgroundColor(colorOriginal);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(fila);
                params.columnSpec = GridLayout.spec(col);
                params.width = 250;
                params.height = 250;
                params.setMargins(8, 8, 8, 8);

                layout.addView(b, params);
                botones[fila][col] = b;

                int finalFila = fila;
                int finalCol = col;

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contador++;
                        cambiarColor(finalFila, finalCol);
                        comprobarVictoria();
                    }
                });
            }
        }

        // Botón Reiniciar
        Button reiniciar = new Button(this);
        reiniciar.setText("Reiniciar");

        GridLayout.LayoutParams paramsReiniciar = new GridLayout.LayoutParams();
        paramsReiniciar.rowSpec = GridLayout.spec(3);
        paramsReiniciar.columnSpec = GridLayout.spec(0, 3); // Ocupa las 3 columnas
        paramsReiniciar.width = GridLayout.LayoutParams.MATCH_PARENT;
        paramsReiniciar.height = GridLayout.LayoutParams.WRAP_CONTENT;
        paramsReiniciar.setMargins(16, 16, 16, 16);

        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarJuego();
            }
        });

        Button pasarActivityPulsaciones = new Button(this);
        pasarActivityPulsaciones.setText("Pulsaciones");
        GridLayout.LayoutParams paramsPulsaciones = new GridLayout.LayoutParams();
        paramsPulsaciones.rowSpec = GridLayout.spec(3);
        paramsPulsaciones.columnSpec = GridLayout.spec(0, 3); // Ocupa las 3 columnas
        paramsPulsaciones.width = GridLayout.LayoutParams.MATCH_PARENT;
        paramsPulsaciones.height = GridLayout.LayoutParams.WRAP_CONTENT;
        paramsPulsaciones.setMargins(16, 160, 16, 16);

        pasarActivityPulsaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarActivity();
            }
        });

        layout.addView(reiniciar, paramsReiniciar);
        layout.addView(pasarActivityPulsaciones, paramsPulsaciones);

        setContentView(layout);
    }


    private void cambiarColor(int fila, int col) {
        // Cambiar color del botón pulsado
        alternarColor(botones[fila][col]);

        // Adyacentes
        if (fila > 0) alternarColor(botones[fila - 1][col]); // arriba
        if (fila < 2) alternarColor(botones[fila + 1][col]); // abajo
        if (col > 0) alternarColor(botones[fila][col - 1]); // izquierda
        if (col < 2) alternarColor(botones[fila][col + 1]); // derecha
    }

    private void alternarColor(Button b) {
        int colorActual = ((ColorDrawable) b.getBackground()).getColor();
        if (colorActual == colorOriginal) {
            b.setBackgroundColor(colorSecundario);
        } else {
            b.setBackgroundColor(colorOriginal);
        }
    }

    private void comprobarVictoria() {
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                int colorActual = ((ColorDrawable) botones[fila][col].getBackground()).getColor();
                if (colorActual != colorSecundario) {
                    return; // Todavía no ha ganado
                }
            }
        }
        Toast.makeText(this, "¡Victoria! 🎉", Toast.LENGTH_LONG).show();
    }

    private void reiniciarJuego() {
        contador= 0;
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                botones[fila][col].setBackgroundColor(colorOriginal);
            }
        }
    }

    public void iniciarActivity() {  // (e)
        Intent intentDatos= new Intent(this, ActivityPulsaciones.class);
        intentDatos.putExtra("NumPulsaciones", contador);
        startActivity(intentDatos);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) { // (f)
        super.onSaveInstanceState(outState);
        outState.putInt("VALOR_CONTADOR",contador);
    }
}