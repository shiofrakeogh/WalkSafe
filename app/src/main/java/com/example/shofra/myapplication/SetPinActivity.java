package eva.walksafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SetPinActivity extends AppCompatActivity {

    EditText setpin;
    EditText confirmpin;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_pin_activity);

        setpin = (EditText) findViewById(R.id.editPin);
        confirmpin = (EditText) findViewById(R.id.confirmPin);
        save = (Button) findViewById(R.id.save_pin);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setpin.getText().toString().length() == 0 || confirmpin.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "You didn't fill both fields dummy", Toast.LENGTH_LONG).show();
                } else {
                    if (setpin.getText().toString().equals(confirmpin.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "PINs match", Toast.LENGTH_SHORT).show();
                        //returnPin();
                        Intent intent = new Intent();
                        intent.putExtra("PIN_STRING", confirmpin.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "PINs don't match", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    //public String returnPin() {
      //  return confirmpin.getText().toString();
    //}
}
