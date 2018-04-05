package com.example.emiltostrup.sensortestassignment;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Accelerometer extends AppCompatActivity implements SensorEventListener {

        TextView txt_accelerometer;
        TextView txt_x;
        TextView txt_y;
        TextView txt_z;
        private SensorManager mSensorManager;
        private Sensor mRotationV, mAccelerometer, mMagnetometer;
        boolean haveSensor = false, haveSensor2 = false;
        float[] rMat = new float[9];
        float[] orientation = new float[3];
        private float[] mLastAccelerometer = new float[3];
        private float[] mLastMagnetometer = new float[3];
        private boolean mLastAccelerometerSet = false;
        private boolean mLastMagnetometerSet = false;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_accelerometer);
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            txt_accelerometer = (TextView) findViewById(R.id.txt_acc);
            txt_x = (TextView) findViewById(R.id.txt_x);
            txt_y = (TextView) findViewById(R.id.txt_y);
            txt_z = (TextView) findViewById(R.id.txt_z);

            start();
        }

        public void stop() {
            if(haveSensor && haveSensor2){
                mSensorManager.unregisterListener(this,mAccelerometer);
                mSensorManager.unregisterListener(this,mMagnetometer);
            }
            else{
                if(haveSensor)
                    mSensorManager.unregisterListener(this,mRotationV);
            }
        }

        @Override
        protected void onPause() {
            super.onPause();
            stop();
        }

        @Override
        protected void onResume() {
            super.onResume();
            start();
        }


        public void start() {
            if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) == null) {
                if ((mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) || (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) == null)) {
                    noSensorsAlert();
                }
                else {
                    mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                    haveSensor = mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
                    haveSensor2 = mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_UI);
                }
            }
            else{
                mRotationV = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                haveSensor = mSensorManager.registerListener(this, mRotationV, SensorManager.SENSOR_DELAY_UI);
            }
        }



        public void noSensorsAlert(){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Your device doesn't support the Accelerometer.")
                    .setCancelable(false)
                    .setNegativeButton("Close",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            alertDialog.show();
        }



        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }


    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(rMat, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(rMat, orientation);
        }


        txt_x.setText("X: " + round(event.values[0]));
        txt_y.setText("Y: " + round(event.values[1]));
        txt_z.setText("Z: " + round(event.values[2]));

    }


    private String round(float val)
    {
        return String.format("%.2f", val);
    }
}
