package com.example.mobdev10;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);
    }
    @NonNull
    @Override
    public Result doWork(){
        Node node = new Node();
        Node[][] map = node.Generation(200);
        Log.i("gg","generated");
        //Toast.makeText(getApplicationContext(),"Map generated",Toast.LENGTH_SHORT).show();
        return Result.success();
    }
}
