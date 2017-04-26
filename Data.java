package eva.walksafe;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Data extends SQLiteOpenHelper{

    private static final String DB_NAME = "Database.db";
    private static final String INFO_TABLE = "info";
    private static final String PIN_COL = "pin";
    private static final String CONTACT_COL = "contact";

    public Data(Context context){

        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String tblCreator = String.format("CREATE TABLE %s (%s TEXT, %s TEXT);", INFO_TABLE,PIN_COL,CONTACT_COL);
        db.execSQL(tblCreator);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){

    }

    //put the pin into the database
    public void writePIN(String pin){

        /*SQLiteDatabase dbpin = getReadableDatabase();
        String query1 = String.format("SELECT %s FROM %s", PIN_COL, INFO_TABLE);
        Cursor cursor = dbpin.rawQuery(query1, null);
        cursor.close();
        dbpin.close();

        SQLiteDatabase db = getWritableDatabase();
        if(cursor == null){//if pin_col is null put in pin
            String query2 = String.format("INSERT INTO %s (%s) VALUES ('%s');", INFO_TABLE, PIN_COL, pin);
            db.execSQL(query2);
        }
        else { // if pin_col is not null so update pin
            String query3 = String.format("UPDATE %s SET %s = '%S'", INFO_TABLE, PIN_COL, pin);
            db.execSQL(query3);
        }*/

        SQLiteDatabase db = getWritableDatabase();
        String query2 = String.format("INSERT INTO %s (%s) VALUES ('%s');", INFO_TABLE, PIN_COL, pin);
        db.execSQL(query2);
    }

    //will get the last entered pin from the database
    public String retrievePIN() {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT %s FROM %s", PIN_COL, INFO_TABLE);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToLast();
        String pin = String.format(cursor.getString(0));
        cursor.close();
        //db.close();

        return pin;
    }

    //put the chosen contact into the database
    public void writeContact(String number){
        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("INSERT INTO %s (%s) VALUES ('%s');", INFO_TABLE, CONTACT_COL, number);
        db.execSQL(query);
    }

    //will get the last chosen contact from the database
    public String retrieveContact(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT %s FROM %s", CONTACT_COL, INFO_TABLE);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToLast();
        String number = String.format(cursor.getString(0));
        cursor.close();
        //db.close();

        return number;
    }
}
