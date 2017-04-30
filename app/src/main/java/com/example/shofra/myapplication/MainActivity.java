package eva.walksafe;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.transition.Fade;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Vibrator;


public class MainActivity extends AppCompatActivity
{
    Intent intent;
    View view;
    private Data database = new Data(this);
    Vibrator vibe;
    boolean vibratorsettings;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView text = (TextView)findViewById(R.id.textTest);
        text.setText("Make sure you set your PIN code and choose your emergency contact!");
        Button pinbtn = (Button)findViewById(R.id.set_pin_btn);
        Button contactbtn = (Button)findViewById(R.id.set_contact_btn);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        vibratorsettings = prefs.getBoolean("vibration",false);
        editor.apply();

        final Button button = (Button) findViewById(R.id.button);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    button.setBackgroundResource(R.drawable.button_pressed);
                    if(vibratorsettings){
                        vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                        long[] pattern = {0, 10000, 100000};
                        vibe.vibrate(pattern, 0);
                    }
                    else {
                        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibe.vibrate(0);
                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    vibe.cancel();
                    button.setBackgroundResource(R.drawable.button1);
                    //Start a timer
                    //Toast.makeText(getApplicationContext(), "Enter your pin", Toast.LENGTH_LONG).show();
                    start();
                    Intent intent = new Intent(MainActivity.this, PinKeypadActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                    ((AlarmManager) getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
                }
                return true;
            }
        });

        pinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPin();
            }
        });

        contactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                //get contact
                startActivityForResult(intent, 1);
            }
        });

        /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        String username = prefs.getString("user_name", null);
        String phoneNumber = contactData.retrieveContactNumber();*/

        /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        String message = prefs.getString("message_preferences",null);
        editor.apply();*/

        /*TextView text = (TextView)findViewById(R.id.textTest);
        text.setText("Message is: " + message);*/

        Button test = (Button)findViewById(R.id.testbtn);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = database.retrieveContact();
                if(number != null) {
                    Toast.makeText(getApplicationContext(), "Number from database is " + number, Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "No contact chosen", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        vibratorsettings = prefs.getBoolean("vibration",false);
        editor.apply();
    }

    //starts the pin keypad activity
    public void start(){
        intent = new Intent(this,PinKeypadActivity.class);
        startActivityForResult(intent, 2);
    }

    public void setPin(){
        intent = new Intent(this,SetPinActivity.class);
        startActivityForResult(intent, 0);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //set the pin
        if(requestCode == 0){
            if(data != null) {
                //put pin in database?
                String chosenPin = data.getStringExtra("PIN_STRING");
                Toast.makeText(this, chosenPin, Toast.LENGTH_LONG).show();
                database.writePIN(chosenPin);
            }
        }

        //choose the emergency contact
        if(requestCode == 1) {
            if (data != null) {
                Uri uri = data.getData();

                if (uri != null) {
                    Cursor c = null;
                    try {
                        c = getContentResolver().query(uri, new String[]{
                                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                                        ContactsContract.CommonDataKinds.Phone.TYPE},
                                null, null, null);

                        if (c != null && c.moveToFirst()) {
                            String number = c.getString(0);
                            int type = c.getInt(1);
                            showSelectedNumber(type, number);
                            //chosenNumber = number;
                        }
                    } finally {
                        if (c != null) {
                            c.close();
                        }
                    }
                }
            }
        }

        //enter pin, check if right, if not send text
        if(requestCode == 2){
            if(data != null) {
                String enteredPin = data.getStringExtra("CHECK_PIN");
                Toast.makeText(this, enteredPin, Toast.LENGTH_LONG).show();

                String pin = database.retrievePIN();
                if (pin.equals(enteredPin)) {
                    Toast.makeText(this, "You entered the right pin!!!!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "You didn't enter your pin, text will be sent", Toast.LENGTH_LONG).show();
                    sendSMS();
                }
            }
        }
    }

    //put chosen contact into toast on this page.
    public void showSelectedNumber(int type, String number) {
        Toast.makeText(this, type + ": " + number, Toast.LENGTH_LONG).show();
        database.writeContact(number);
    }

    public void sendSMS(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        String setmessage = prefs.getString("message_preferences",null);
        editor.apply();

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


   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
    }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
     // Handle action bar item clicks here. The action bar will
     // automatically handle clicks on the Home/Up button, so long
     // as you specify a parent activity in AndroidManifest.xml.
     int id = item.getItemId();

     //noinspection SimplifiableIfStatement
     if (id == R.id.action_settings) {
         Intent intent = new Intent(this,SettingsActivity.class);
         startActivity(intent);
         overridePendingTransition(R.anim.up_in, R.anim.up_out);
         //return true;
     }

     return super.onOptionsItemSelected(item);
     }
}
