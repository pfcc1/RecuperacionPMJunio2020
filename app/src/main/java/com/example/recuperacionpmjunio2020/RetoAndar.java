package com.example.recuperacionpmjunio2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RetoAndar extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    int tiempoRefresco = 500;
    EditText editTextDistanciaAAndar;
    TextView textViewMetros, textviewTextDistanciaRecorrida;
    Button buttonIniciar;
    LocationManager locationManager;
    LocationListener locationListener;
    double longitudInicial = 0, latitudInicial = 0;
    int banderaInicial = 0, banderaSegunda = 0;
    String ID_CANAL = "mi canal favorito";
    int CODIGO_RESPUESTA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto_andar);

        editTextDistanciaAAndar=findViewById(R.id.editTextDistanciaAAndar);
        textViewMetros=findViewById(R.id.textViewMequedaPorRecorrer);
        textviewTextDistanciaRecorrida=findViewById(R.id.textViewDistanciaRecorrida);
        buttonIniciar=findViewById(R.id.buttonIniciarRecorrido);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted

            buttonIniciar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textviewTextDistanciaRecorrida.setText("0");
                    textViewMetros.setText("0");
                    if (editTextDistanciaAAndar.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "No ha introducido los metros a andar", Toast.LENGTH_SHORT).show();
                    }


                    locationManager = (LocationManager) RetoAndar.this.getSystemService(Context.LOCATION_SERVICE);
                    locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            // textViewMetros.setText("ALtitud: "+location.getAltitude()+" Latitud: "+location.getLatitude());

                            if (banderaInicial != 1 && banderaSegunda == 0) {
                                banderaInicial = 1;
                                longitudInicial = location.getLongitude();
                                latitudInicial = location.getLatitude();
                            } else if (banderaInicial == 1) {
                                banderaSegunda = 1;
                                Location locationInicial = new Location("locationInicial");
                                locationInicial.setLongitude(longitudInicial);
                                locationInicial.setLatitude(latitudInicial);


                                System.out.println("Location Inicial Latitud: " + locationInicial.getLatitude());
                                System.out.println("Location Inicial Longitud: " + locationInicial.getLongitude());

                                System.out.println("Location Destino Latitud: " + location.getLatitude());
                                System.out.println("Location Destino Longitud: " + location.getLongitude());


                                double dis = getDistancia(locationInicial, location);


                                if (dis <= Integer.parseInt(editTextDistanciaAAndar.getText().toString())) {
                                    double res = redondearDecimales(dis, 2);
                                    textviewTextDistanciaRecorrida.setText("Distancia Recorrida: " + res + " metros");
                                    System.out.println("Distancia: " + res);


                                    double Recorrer = res - Integer.parseInt(editTextDistanciaAAndar.getText().toString());

                                    double red = redondearDecimales(Recorrer, 2);

                                    textViewMetros.setText("Me Queda por Recorrer:  " + red + " metros");

                                } else if (dis > Integer.parseInt(editTextDistanciaAAndar.getText().toString())) {
                                    //Toast.makeText(getApplicationContext(), "Distancia Recorrida", Toast.LENGTH_SHORT).show();
                                    lanzarNotificacionConFoto();
                                }

                            }


                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    };

                    int permissioncheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, tiempoRefresco, 0, locationListener);
                }
            });
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    else{
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, tiempoRefresco, 0, locationListener);
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(),"No tienes Permiso de GPS",Toast.LENGTH_SHORT).show();

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public  static  double getDistancia (Location pointStart, Location pointEnd) {


        double startLatitude = pointStart.getLatitude() ;
        double startLongitude = pointStart.getLongitude() ;
        double endLatitude = pointEnd.getLatitude () ;
        double endLongitude = pointEnd.getLongitude() ;
        float [] result = new  float [1];
        Location.distanceBetween (startLatitude, startLongitude, endLatitude, endLongitude, result);

        return result [0]/ 10;

    }

    public static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }

    private void lanzarNotificacionConFoto() {
        int notifyId = 4;
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, ID_CANAL)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Objetivo Conseguido")
                        .setAutoCancel(true);

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.copa)).build();
        builder.setStyle(bigPictureStyle);

        Intent intent = getIntent();

        intent.putExtra("SOY", "Notificicación con foto");

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(RetoAndar.this);
        taskStackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, CODIGO_RESPUESTA, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
        } else {
            String idChanel = "4";
            String nombreCanal = "micanal4";

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
}
