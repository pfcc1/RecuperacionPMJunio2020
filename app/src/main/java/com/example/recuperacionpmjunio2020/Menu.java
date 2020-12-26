package com.example.recuperacionpmjunio2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

Button buttonSonido,buttonAgregarTarea,buttonMostrarTareas,buttonRealizarTarea,buttonBorrarTareas,buttonSalir,buttonJuego,buttonRetoAndar;
MediaPlayer mediaPlayer;
ManejadorBD manejadorBD;
ImageView imageView;
TextView mostrarNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //mediaPlayer = MediaPlayer.create(this, R.raw.vuelve);
        buttonSonido = findViewById(R.id.buttonVentanaSonido);
        buttonRetoAndar=findViewById(R.id.buttonRetoAndar);
        buttonAgregarTarea = findViewById(R.id.buttonAgregarTareas);
        buttonMostrarTareas = findViewById(R.id.buttonMostrarTareas);
        buttonRealizarTarea=findViewById(R.id.buttonRealizarTarea);
        buttonBorrarTareas=findViewById(R.id.buttonBorrarTareas);
        buttonJuego=findViewById(R.id.buttonModoJuego);
        buttonSalir=findViewById(R.id.buttonSalir);
        imageView=findViewById(R.id.imageViewMenu);
        mostrarNick=findViewById(R.id.textViewNickMenu);

        /*
         * Una vez que hemos accedido por primera vez, para guardar la imagen anterior tenemos que hacer lo siguiente:
         *
         * */
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String mImageUri = preference.getString("image", null);

        if (mImageUri != null) {
            imageView.setImageURI(Uri.parse(mImageUri));
        }
        /*
         * Guardar Nick, y lo muestro en el menú principal
         * */

        SharedPreferences prefe=getSharedPreferences("datosnick", Context.MODE_PRIVATE);
        String valor=prefe.getString("nombre","");
        mostrarNick.setText(valor);
        Toast.makeText(this, "aaaaa "+valor, Toast.LENGTH_SHORT).show();

        manejadorBD=new ManejadorBD(this);

        buttonSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentSonido = new Intent(getApplicationContext(), Sonido.class);
                startActivity(intentSonido);

            }
        });

        buttonRetoAndar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRetoAndar=new Intent(getApplicationContext(),RetoAndar.class);
                startActivity(intentRetoAndar);
            }
        });

        buttonAgregarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAgregarTarea = new Intent(getApplicationContext(), AgregarTareas.class);
                startActivity(intentAgregarTarea);
            }
        });

        buttonRealizarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRealizarTarea=new Intent(getApplicationContext(),RealizarTarea.class);
                startActivity(intentRealizarTarea);
            }
        });

        buttonBorrarTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBorrarTarea=new Intent(getApplicationContext(),BorrarTareas.class);
                startActivity(intentBorrarTarea);
            }
        });

        buttonMostrarTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMostrarTareas = new Intent(getApplicationContext(), MostrarTareas.class);
                startActivity(intentMostrarTareas);
            }
        });
     /*











        buttonJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentJuego=new Intent(getApplicationContext(),ModoJuego.class);
                startActivity(intentJuego);
            }
        });
*/
        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Cursor cursor=manejadorBD.obtenerNumerodeTareasNoRealizadas();
                int numero=0;

                   while (cursor.moveToNext()){
                          numero= cursor.getInt(0);

                    }

                    cursor.close();



              Toast.makeText(getApplicationContext(),"Numero de Tareas no Realizadas: "+numero,Toast.LENGTH_LONG).show();

             // finish();

                finishAffinity();
            }
        });
    }



    }

