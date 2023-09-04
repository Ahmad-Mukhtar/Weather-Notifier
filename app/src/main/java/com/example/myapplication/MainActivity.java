package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
EditText editText;
Button btn;
public static final int NOTIFICATION_ID=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.button);
        editText=findViewById(R.id.myedittext);
        WorkManager.getInstance().cancelAllWork();
        btn.setOnClickListener(v -> {
            if (editText.getText().toString().isEmpty()||editText.getText()==null)
            {
                Toast.makeText(getApplicationContext(),"No city Entered",Toast.LENGTH_LONG).show();
            }
            else
            {

                StartWorker(editText.getText().toString(),2);
                Toast.makeText(getApplicationContext(),"You will receive notifications regarding tomorrow's weather for city "+editText.getText().toString(),Toast.LENGTH_LONG).show();



            }

        });

    }

    static void StartWorker(String city,int duration)
    {

        Data data=new Data.Builder().putString("city",city).build();
        OneTimeWorkRequest workRequest=new OneTimeWorkRequest.Builder(NotificationWorker.class).setInputData(data)
                .setInitialDelay(duration, TimeUnit.SECONDS).build();
        WorkManager.getInstance().enqueue(workRequest);

    }


}