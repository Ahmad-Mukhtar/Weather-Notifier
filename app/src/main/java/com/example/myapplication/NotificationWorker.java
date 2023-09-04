package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.atomic.AtomicBoolean;

public class NotificationWorker extends Worker {

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Data d=getInputData();
        String city=d.getString("city");
        getWeather(city, isNotified -> {
            if(isNotified) {
                MainActivity.StartWorker(city, 20);
            }
            else {
                MainActivity.StartWorker(city, 10);
            }

        });
        return Result.success();
    }

    public interface WeatherCallback {
        void onWeatherReceived(boolean isClear);
    }
    public void getWeather(String city,WeatherCallback callback)
    {
        AtomicBoolean isSent= new AtomicBoolean(false);
        String url= "https://api.openweathermap.org/data/2.5/forecast?q="+city+"&cnt=8&APPID="+BuildConfig.API_KEY;
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray=response.getJSONArray("list");
                String weather=jsonArray.getJSONObject(7).getJSONArray("weather").getJSONObject(0).get("main").toString();
                if(weather.equals("Clear")) {
                    sendNotification(weather);
                    isSent.set(true);
                }
            } catch (JSONException e) {
                Toast.makeText(NotificationWorker.this.getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

            }
            callback.onWeatherReceived(isSent.get());
        }, error -> {
            Toast.makeText(NotificationWorker.this.getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
        }

        );
        queue.add(jsonObjectRequest);
    }

    private void sendNotification(String weather)
    {
        final String CHANNEL_ID="Weather_Notification";
        Intent killAppIntent = new Intent(this.getApplicationContext(), StopWorker.class);
        killAppIntent.setAction("KILL_APP_ACTION"); // You can define any action string you like
        PendingIntent pendingKillAppIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, killAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder=new
                NotificationCompat.
                        Builder(getApplicationContext(),CHANNEL_ID);

        builder.setSmallIcon(R.drawable.ic_noti)
                .setContentTitle(weather)
                .setContentText("Its gonna Rain tomorrow Please be Careful")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(null)
                .addAction(R.mipmap.ic_launcher,"Stop Notifications",pendingKillAppIntent);


        NotificationManager notificationManager= (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=notificationManager.getNotificationChannel(CHANNEL_ID);
            if(notificationChannel==null){
                int importance=NotificationManager.IMPORTANCE_HIGH;
                notificationChannel=new NotificationChannel(CHANNEL_ID,"Its gonna Rain tomorrow Please be Careful",importance);
                notificationChannel.setLightColor(Color.BLUE);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }

        }

        notificationManager.notify(MainActivity.NOTIFICATION_ID,builder.build());


    }





}
