package com.ncbi.mysercice1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class BoundService1 extends Service {
    private MediaPlayer mediaPlayer ;
    private MyBinder binder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.lol);
        mediaPlayer.setLooping(true);
    }

    class MyBinder extends Binder{
        public void start(){
            mediaPlayer.start();
        }
        public void pause(){
            mediaPlayer.pause();
        }
        public void stop(){
            mediaPlayer.stop();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
