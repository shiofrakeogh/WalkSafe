package eva.walksafe;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class PinKeypadActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn[] = new Button[11];
    EditText edittext;
    Data database = new Data(this);
    String setmessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_keypad);
        runProgressBar();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        setmessage = prefs.getString("message_preferences",null);
        editor.apply();

        btn[0] = (Button)findViewById(R.id.btn1);
        btn[1] = (Button)findViewById(R.id.btn2);
        btn[2] = (Button)findViewById(R.id.btn3);
        btn[3] = (Button)findViewById(R.id.btn4);
        btn[4] = (Button)findViewById(R.id.btn5);
        btn[5] = (Button)findViewById(R.id.btn6);
        btn[6] = (Button)findViewById(R.id.btn7);
        btn[7] = (Button)findViewById(R.id.btn8);
        btn[8] = (Button)findViewById(R.id.btn9);
        btn[9] = (Button)findViewById(R.id.btn0);
        btn[10] = (Button)findViewById(R.id.btnclear);

        for(int i = 0; i < 11; i++){
            btn[i].setOnClickListener(this);
        }

        final Button enterbtn = (Button)findViewById(R.id.btnenter);
        enterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edittext == null) {
                    Toast.makeText(getApplicationContext(), "You didn't enter anything", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "PIN Entered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("CHECK_PIN", edittext.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
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
                edittext = (EditText)findViewById(R.id.edittext);
                edittext.setText("");
                break;
        }
    }

    public void addtoarray(String nums){
        edittext = (EditText)findViewById(R.id.edittext);
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
                bar.setVisibility(View.INVISIBLE);

                if(edittext == null) {
                    Toast.makeText(getApplicationContext(), "You didn't enter anything", Toast.LENGTH_SHORT).show();
                    try {
                        String phoneno = database.retrieveContact();
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneno, null, setmessage, null, null);
                        Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "SMS failed, please try again later!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "PIN Entered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("CHECK_PIN", edittext.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }

                Toast.makeText(getApplicationContext(), "Timer finished", Toast.LENGTH_LONG).show();
                finish();

            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        //This prevents the user from exiting the activity by pressing the back button
        //The acivity ends only if the timer runs out or a pin is entered
    }
}
