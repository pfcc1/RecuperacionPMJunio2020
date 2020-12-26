package com.example.recuperacionpmjunio2020;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MostrarTareas extends AppCompatActivity {

    ManejadorBD manejadorBD;
    ListView lista;
    RadioButton radioButtonRealizadas,radioButtonNorealizadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_tareas);

        lista=findViewById(R.id.listview);
        radioButtonNorealizadas=findViewById(R.id.radioButtonNoRealizadas);
        radioButtonRealizadas=findViewById(R.id.radioButtonRealizadas);
        manejadorBD=new ManejadorBD(this);

       radioButtonRealizadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = manejadorBD.listarTareasRealizadas();

                ArrayAdapter<String> adapter;
                List<String> list = new ArrayList<String>();

                if ((cursor != null) && (cursor.getCount() > 0)) {
                    while (cursor.moveToNext()) {
                        String fila = "";
                        fila += "ID: " + cursor.getString(0);
                        fila += " FECHA: " + cursor.getString(1);
                        fila += " DESCRIPCIÓN: " + cursor.getString(2);
                        fila += " ESTADO: " + cursor.getString(3);
                        list.add(fila);

                    }

                    adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
                    lista.setAdapter(adapter);
                    cursor.close();
                } else {
                    Toast.makeText(MostrarTareas.this, "Nada que mostarr", Toast.LENGTH_SHORT).show();
                }
            }
        });

       radioButtonNorealizadas.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Cursor cursor = manejadorBD.listarTareasNoRealizadas();

               ArrayAdapter<String> adapter;
               List<String> list = new ArrayList<String>();

               if ((cursor != null) && (cursor.getCount() > 0)) {
                   while (cursor.moveToNext()) {
                       String fila = "";
                       fila += "ID: " + cursor.getString(0);
                       fila += " FECHA: " + cursor.getString(1);
                       fila += " DESCRIPCIÓN: " + cursor.getString(2);
                       fila += " ESTADO: " + cursor.getString(3);
                       list.add(fila);
                   }
                   adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
                   lista.setAdapter(adapter);
                   cursor.close();
               }
               else{
                   Toast.makeText(MostrarTareas.this, "Nada que mostrar", Toast.LENGTH_SHORT).show();
               }



           }
       });



    }

}
