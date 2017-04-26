package eva.walksafe;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;


public class PinKeypadActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn[] = new Button[11];
    EditText edittext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_keypad);
        //runProgressBar();

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
                else{
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
}
