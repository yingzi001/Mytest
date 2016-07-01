package com.ncbi.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyServiceForMusic extends Service {
    MediaPlayer mediaPlayer ;
    public MyServiceForMusic() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer.setLooping(true);
        mediaPlayer = MediaPlayer.create(this,);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
