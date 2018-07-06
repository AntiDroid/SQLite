package com.example.talip.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.talip.sqlite.R;

public class MainActivity extends AppCompatActivity {

    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = openOrCreateDatabase("mydb", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS mydata(key text, val integer);");

        Cursor results = db.rawQuery("SELECT val FROM mydata WHERE key = 'counter';", null);

        if(results.getCount() != 0) {
            results.moveToFirst();
            counter = results.getInt(0);
        } else {
            db.execSQL("INSERT INTO mydata (key, val) VALUES ('counter', 0);");
            counter = 0;
        }

        db.close();

        TextView tv = findViewById(R.id.counterText);
        tv.setText(Integer.toString(counter));
    }

    @Override
    protected void onStop() {

        SQLiteDatabase db = openOrCreateDatabase("mydb", MODE_PRIVATE, null);
        db.execSQL("UPDATE mydata SET val="+counter+" WHERE key = 'counter';");
        db.close();

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        Log.d("TALIPVURAL", "Destroyed!");
        super.onDestroy();
    }

    public void onCounterClick(View v) {

        counter++;

        TextView tv = findViewById(R.id.counterText);
        tv.setText(Integer.toString(counter));
    }
}
