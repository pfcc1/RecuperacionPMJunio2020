package com.example.recuperacionpmjunio2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class BorrarTareas extends AppCompatActivity {

    EditText editTextBorrarTarea;
    Button buttonBorrarTarea;
    ManejadorBD manejadorBD;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar_tareas);

        editTextBorrarTarea=findViewById(R.id.editTextIdTareaABorrar);
        buttonBorrarTarea=findViewById(R.id.buttonTareaABorrar);
        manejadorBD=new ManejadorBD(this);
        aSwitch=findViewById(R.id.switchBorrarTarea);

        buttonBorrarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(aSwitch.isChecked()) {

                    boolean res = manejadorBD.borrar(editTextBorrarTarea.getText().toString());
                    if (res == true) {
                        Toast.makeText(getApplicationContext(), "Tarea Borrada correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Tarea no borrada", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"No has activado la opción de borrar tarea",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
