package com.bh90210.cp500;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import cpfiveoo.Cpfiveoo;

public class TestJobService extends JobService {
    private static final String TAG = "SyncService";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onStartJob(JobParameters params) {
        Intent service = new Intent(getApplicationContext(), ScrollingActivity.class);
        getApplicationContext().startService(service);

        PersistableBundle pb=params.getExtras();
        String id = pb.getString("id");
        Cpfiveoo.scheduledPostUpload(id);
        showNotification();
        //Util.scheduleJob(getApplicationContext()); // reschedule the job

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    public void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Upload")
                .setSmallIcon(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
                .setContentTitle("Message from CP500")
                .setContentText("Your photo was succesufully uploaded?!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                //.setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());
    }
}