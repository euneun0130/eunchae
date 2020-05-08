package com.example.map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void sendMessage(View view) {
        Intent button2 = new Intent(this, Main2Activity.class);
        startActivity(button2);
    }
    public void sendMessage2(View view) {
        Intent button7 = new Intent(this, Main5Activity.class);
        startActivity(button7);
    }
    public void sendMessage3(View view) {
        Intent floatingActionButton = new Intent(this, Map.class);
        startActivity(floatingActionButton);
    }
}
