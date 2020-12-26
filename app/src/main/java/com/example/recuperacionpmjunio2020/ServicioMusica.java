package com.example.recuperacionpmjunio2020;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class ServicioMusica extends Service {
    MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Has llamado al servicio musical.", Toast.LENGTH_LONG).show();
        mediaPlayer = MediaPlayer.create(this, R.raw.yirumahope);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // mediaPlayer.start();

        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            Toast.makeText(this, "Mi servicio musical se ha pausado", Toast.LENGTH_LONG).show();
        }
        else
        {
            mediaPlayer.start();
            Toast.makeText(this, "Mi servicio musical se ha reanudado.", Toast.LENGTH_LONG).show();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        Toast.makeText(this, "Mi servicio musical se ha cerrado.", Toast.LENGTH_LONG).show();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
