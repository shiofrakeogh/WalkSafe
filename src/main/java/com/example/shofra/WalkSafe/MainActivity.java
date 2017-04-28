package com.example.shofra.WalkSafe;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity
{
    private MediaPlayer mediaPlayer, mediaPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = (Button) findViewById(R.id.button);
       // mediaPlayer = MediaPlayer.create(this, R.raw.clickon1);
        //mediaPlayer2 = MediaPlayer.create(this, R.raw.clickoff1);

        //final Button button2 = (Button) findViewById(R.id.button2);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    button.setBackgroundResource(R.drawable.button2);
                   // mediaPlayer.start();



                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button.setBackgroundResource(R.drawable.button1);
                   // mediaPlayer2.start();

                    //setContentView(R.layout.timer);
                    //Start a timer
                    Intent intent = new Intent(MainActivity.this, keypadActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                    ((AlarmManager) getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
            }
                return true;
            }
        });
    }

}


