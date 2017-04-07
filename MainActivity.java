package com.example.shofra.myapplication;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //final TextView timerValue1 = (TextView) findViewById(R.id.timerValue);
        final Button button = (Button) findViewById(R.id.button);
        final Button button2 = (Button) findViewById(R.id.button2);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    button.setBackgroundResource(R.drawable.button_pressed);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    //button.setBackgroundResource(R.drawable.button);
                    //setContentView(R.layout.timer);
                    //Start a timer
                    //Toast.makeText(getApplicationContext(), "Enter your pin", Toast.LENGTH_LONG).show();
                    //Intent intent = new Intent(getApplicationContext(), myBroadcastReceiver.class);
                    /*Intent intent = new Intent(getApplicationContext(), keypadActivity.class);
                    //PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                            + 1000, intent);
                    Toast.makeText(getApplicationContext(), "Alarm set in one second", Toast.LENGTH_LONG).show();*/
                    Intent intent = new Intent(MainActivity.this, keypadActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                    ((AlarmManager) getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, pendingIntent);
            }
                return true;
            }
        });
    }
}


