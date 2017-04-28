package com.example.shofra.WalkSafe;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by Shíofra on 22/03/2017.
 */

    public class keypadActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn[] = new Button[11];
    EditText edittext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keypad);
        runProgressBar();

        btn[0] = (Button) findViewById(R.id.btn1);
        btn[1] = (Button) findViewById(R.id.btn2);
        btn[2] = (Button) findViewById(R.id.btn3);
        btn[3] = (Button) findViewById(R.id.btn4);
        btn[4] = (Button) findViewById(R.id.btn5);
        btn[5] = (Button) findViewById(R.id.btn6);
        btn[6] = (Button) findViewById(R.id.btn7);
        btn[7] = (Button) findViewById(R.id.btn8);
        btn[8] = (Button) findViewById(R.id.btn9);
        btn[9] = (Button) findViewById(R.id.btn0);
        btn[10] = (Button) findViewById(R.id.btnclear);

        for (int i = 0; i < 11; i++) {
            btn[i].setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                addtoarray("1");
                break;
            case R.id.btn2:
                addtoarray("2");
                break;
            case R.id.btn3:
                addtoarray("3");
                break;
            case R.id.btn4:
                addtoarray("4");
                break;
            case R.id.btn5:
                addtoarray("5");
                break;
            case R.id.btn6:
                addtoarray("6");
                break;
            case R.id.btn7:
                addtoarray("7");
                break;
            case R.id.btn8:
                addtoarray("8");
                break;
            case R.id.btn9:
                addtoarray("9");
                break;
            case R.id.btn0:
                addtoarray("0");
                break;
            case R.id.btnclear:
                edittext = (EditText) findViewById(R.id.edittext);
                edittext.setText("");
                break;
        }
    }

    public void addtoarray(String nums) {
        edittext = (EditText) findViewById(R.id.edittext);
        edittext.append(nums);
    }


        public void runProgressBar() {

            final ProgressBar bar = (ProgressBar) findViewById(R.id.progress);

            bar.setProgress(0);
            final int seconds = 8 * 1000; // 8 seconds in milli seconds

            /** CountDownTimer starts with 8 seconds and every onTick is 1 second */
            CountDownTimer cdt = new CountDownTimer(seconds, 1000) {

                public void onTick(long millisUntilFinished) {

                    bar.setProgress((int) (millisUntilFinished/100));
                }

                public void onFinish() {
                    // DO something when 8 seconds is up
                    Toast.makeText(getApplicationContext(), "Timer finished", Toast.LENGTH_LONG).show();
                    //create an intent here?? Too many things happening??
                    try {
                        String phoneno = "0851677141";
                        String message = "Well";
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneno, null, message, null, null);
                        Toast.makeText(getApplicationContext(), "SMS Sent!",
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "SMS failed, please try again later!",
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }.start();
        }
}

