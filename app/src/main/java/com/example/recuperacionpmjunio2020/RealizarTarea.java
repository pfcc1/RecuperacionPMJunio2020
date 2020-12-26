package com.example.recuperacionpmjunio2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RealizarTarea extends AppCompatActivity {
Button buttonAceptarRealizarTarea;
EditText editTextIdTareaaRealizar;
Switch aSwitch;
ManejadorBD manejadorBD;
    String ID_CANAL = "mi canal favorito";
    int CODIGO_RESPUESTA = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_tarea);

        buttonAceptarRealizarTarea=findViewById(R.id.buttonAceptarRealizarTarea);
        editTextIdTareaaRealizar=findViewById(R.id.editTextIdTareaARealizar);
        aSwitch=findViewById(R.id.switchRealizarTarea);
        manejadorBD=new ManejadorBD(this);

        buttonAceptarRealizarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()){
                    boolean actualizar=manejadorBD.actualizarTareaARealizada(editTextIdTareaaRealizar.getText().toString(),"Realizada");

                    if(actualizar==true){
                        //manejadorNotificaciones.lanzarNotificacion();
                        Toast.makeText(getApplicationContext(),"Tarea Realizada Correctamente",Toast.LENGTH_SHORT).show();


                        //Obtener Hora Actual

                        Date date=new Date();
                        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss");

                        String descripcion="";
                        Cursor cursor = manejadorBD.obtenerNombreTareaRealizada(editTextIdTareaaRealizar.getText().toString());

                        if ((cursor != null) && (cursor.getCount() > 0)){
                            while (cursor.moveToNext()){
                                descripcion = cursor.getString(2);

                            }
                            cursor.close();
                        }


                        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                        Intent batteryStatus = registerReceiver(null, ifilter);

                        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                        float batteryPct = level / (float)scale*100;

                        int notifyId = 3;

                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(getApplicationContext(), ID_CANAL)
                                        .setSmallIcon(R.drawable.ic_launcher_background)
                                        .setContentTitle("Tarea Realizada")
                                        .setAutoCancel(true)
                                        .setContentText("Tarea "+descripcion+" realizada a las "+hourdateFormat.format(date)+" h"+

                                                " y Nivel de Bateria: "+Math.round(batteryPct)+"%");

                        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
                        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.ok)).build();
                        builder.setStyle(bigPictureStyle);



                        Intent intent = getIntent();


                        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(RealizarTarea.this);
                        taskStackBuilder.addNextIntent(intent);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), CODIGO_RESPUESTA, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(pendingIntent);

                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
                        } else {
                            String idChanel = "2";
                            String nombreCanal = "micanal2";

                            NotificationChannel channel = new NotificationChannel(idChanel, nombreCanal, NotificationManager.IMPORTANCE_DEFAULT);

                            channel.enableLights(true);
                            channel.setLightColor(Color.RED);
                            channel.setShowBadge(true);
                            channel.enableVibration(true);
                            builder.setChannelId(idChanel);
                            notificationManager.createNotificationChannel(channel);
                        }

                        notificationManager.notify(notifyId, builder.build());



                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Tarea no Realizada",Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No has activado la opción de seleccionar tarea",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
