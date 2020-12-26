package com.example.recuperacionpmjunio2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgregarTareas extends AppCompatActivity {

    Button buttonInsertar;
    EditText editTextDescripcion;
    ManejadorBD manejadorBD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tareas);

        buttonInsertar=findViewById(R.id.buttonInsertarTarea);
        editTextDescripcion=findViewById(R.id.editTextDescripcionTarea);
        manejadorBD=new ManejadorBD(this);

        buttonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String estado="No realizada";
                Date date=new Date();
                DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");

                boolean resultado = manejadorBD.insertar(hourdateFormat.format(date),editTextDescripcion.getText().toString(),estado);

                if (resultado) {
                    Toast.makeText(AgregarTareas.this, "Insertado registro correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AgregarTareas.this, "Hubo un error en la inserción. ", Toast.LENGTH_SHORT).show();

                }

            }
        });



    }
}
