package com.example.diploma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TextView tv = findViewById(R.id.textView);
//        tv.bringToFront();

//        Timer t=new Timer();
//        t.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//            }
//        }, 2000);


        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }, 1000);
//        Button btnStart = findViewById(R.id.btnStart);
//
//        btnStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), HomeActivity.class);
//
//                startActivity(intent);
//            }
//        });

    }


}


// Ctrl Alt L