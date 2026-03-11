package com.pmm.puzlebotones;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityPulsaciones extends AppCompatActivity {

    private TextView txtPulsaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pulsaciones);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras= getIntent().getExtras();
        int numPulsaciones= extras.getInt("NumPulsaciones");

        txtPulsaciones= (TextView)findViewById(R.id.textPulsaciones); // (a)
        txtPulsaciones.setText("Número de pulsaciones: " + numPulsaciones);
    }
}