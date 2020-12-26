package com.example.recuperacionpmjunio2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Sonido extends AppCompatActivity {

    Button buttonPausar,buttonParar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonido);

        buttonPausar=findViewById(R.id.buttonPausar);
        buttonParar=findViewById(R.id.buttonParar);

        startService(new Intent(getApplicationContext(),ServicioMusica.class));

        buttonPausar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(getBaseContext(), ServicioMusica.class));
            }
        });

        buttonParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getApplicationContext(),ServicioMusica.class));
                finish();
            }
        });

    }
}
