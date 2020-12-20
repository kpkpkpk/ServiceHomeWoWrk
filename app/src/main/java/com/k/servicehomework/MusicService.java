package com.k.servicehomework;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MusicService extends Service {
    private MediaPlayer mediaPlayer=new MediaPlayer();
    Context context=this;

    //создаем поле по которому будет искать наш сервис ресивер
    public static String MUSIC_SERVICE="MUSIC_SERVICE";
    //ключ по которому будут передаваться данные с интента
    public static String MUSIC_INTENT_FILTER="MUSIC_INTENT_FILTER";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"Service is created",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer=null;
        Toast.makeText(this,"Service is destroyed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        Toast.makeText(this, "Service is started ", Toast.LENGTH_SHORT).show();

        Thread musicThread=new Thread(new Music());
            musicThread.start();
        return START_NOT_STICKY;

    }


    private class Music implements Runnable{

        @Override
        public void run() {
            try {
                String link="https://dl1.mp3party.net/download/9688165";
                mediaPlayer.setDataSource(link);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("MUSIC_LOG","couldn't download track");
                e.printStackTrace();
            }


        }
    }
}

