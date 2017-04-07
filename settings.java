package com.example.shofra.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

/**
 * Created by Shíofra on 29/03/2017.
 */

public class settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Button settingsButtons[] = new Button[3];
        settingsButtons[0] = (Button) findViewById(R.id.home);
        settingsButtons[1] = (Button) findViewById(R.id.selectContact);
        settingsButtons[2] = (Button) findViewById(R.id.pin);

        for (int i = 0; i < 3; i++) {
            settingsButtons[i].setOnClickListener((View.OnClickListener) this);
        }
    }

        /*Button selectContact = (Button) findViewById(R.id.selectContact);
        Button enterPin = (Button) findViewById(R.id.pin);
        Button home = (Button) findViewById(R.id.home);
        selectContact.setOnClickListener(new onClickListener());
        enterPin.setOnClickListener(onClickListener);
        home.setOnClickListener(onClickListener);*/

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.selectContact:
                Cursor contacts = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                String aNameFromContacts[] = new String[contacts.getCount()];
                String aNumberFromContacts[] = new String[contacts.getCount()];
                int i = 0;

                int nameFieldColumnIndex = contacts.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                int numberFieldColumnIndex = contacts.getColumnIndex(ContactsContract.PhoneLookup.NUMBER);

                while (contacts.moveToNext()) {

                    String contactName = contacts.getString(nameFieldColumnIndex);
                    aNameFromContacts[i] = contactName;

                    String number = contacts.getString(numberFieldColumnIndex);
                    aNumberFromContacts[i] = number;
                    i++;
                }

                contacts.close();
        }
    }
}

