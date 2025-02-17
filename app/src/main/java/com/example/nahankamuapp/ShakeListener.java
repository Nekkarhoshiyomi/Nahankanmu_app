package com.example.nahankamuapp;

import android.util.Log;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeListener implements SensorEventListener {

    private static final float SHAKE_THRESHOLD = 40.0f;
    private static final int SHAKE_WAIT_TIME_MS = 500;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private OnShakeListener shakeListener;
    private long lastShakeTime = 0;

    public interface  OnShakeListener {
        void onShake();
    }

    public ShakeListener(Context context){

        Log.d("test", "This is second test");

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null){
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (accelerometer == null){
                Log.e("ShakeListener", "Can't find kasokudo sensor");
            }else{
                Log.d("ShakeListener", "Get kasokudo sensor");
            }
        }else{
            Log.e("ShakeListener", "Can't get kasokudo sensor");
        }
    }

    public void setOnShakeListener(OnShakeListener listener){
        this.shakeListener = listener;
        Log.d("test", "This is third test");
    }

    public void register(){
        if (sensorManager != null && accelerometer != null){
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
            Log.d("ShakeListener", "Register sensorManager");
        }
    }

    public void unregister(){
        if (sensorManager != null){
            sensorManager.unregisterListener(this);
            Log.d("ShakeListener", "Release Sensor Listener");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event){

        Log.d("test", "This is fourth test");

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            Log.d("ShakeListener", "x:" + x + ", y:l" + y + ", z:" + z);

            float gForce = (float) Math.sqrt(x * x + y * y + z * z);

            if (gForce > SHAKE_THRESHOLD){
                final long now = System.currentTimeMillis();

                if (lastShakeTime + SHAKE_WAIT_TIME_MS > now){
                    return;
                }
                lastShakeTime = now;

                if (shakeListener != null){
                    Log.d("ShakeListener", "Shaking");
                    shakeListener.onShake();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }
}
