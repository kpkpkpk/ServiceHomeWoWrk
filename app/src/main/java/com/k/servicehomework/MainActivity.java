package com.k.servicehomework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private TextView textView;
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);
        //регистрируем ресивер
        registerReceiver(receiver,new IntentFilter(MusicService.MUSIC_SERVICE));
        //получаем список датчиков
        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        //проверка на существование
        if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)!=null) {
            //присваеваем тип сенсора
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            Log.d("MainActivityLog","sensor is created");
            //
        }else{
            Toast.makeText(this, "Light sensor not found", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Light sensor not found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_FASTEST);
    }
    @Override
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        stopService(new Intent(this,MusicService.class));
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] lightTestimony=sensorEvent.values.clone();
        textView.setText(lightTestimony[0]+" lx");
        if(lightTestimony[0] == 0.0) {
            startService(new Intent(this,MusicService.class));
        }else{
            stopService(new Intent(this,MusicService.class));
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}