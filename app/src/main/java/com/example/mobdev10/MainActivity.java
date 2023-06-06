package com.example.mobdev10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mobdev10.databinding.ActivityMainBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ExecutorService pool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(this.getLayoutInflater());

        pool = Executors.newSingleThreadExecutor();
        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pool.execute(new GenerationRunnable());
            }
        });

        OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(MyWorker.class).build();
        binding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance(MainActivity.this).enqueue(myWorkRequest);
            }
        });
        WorkManager.getInstance(MainActivity.this).getWorkInfoByIdLiveData(myWorkRequest.getId())
                        .observe(MainActivity.this, workInfo -> {
                            Toast.makeText(MainActivity.this,
                                    "Worker generation "+workInfo.getState().toString(),
                                    Toast.LENGTH_SHORT).show();
                        });

        setContentView(binding.getRoot());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pool.shutdown();
    }

    public class GenerationRunnable implements Runnable{
        Handler mHandler;
        public GenerationRunnable(){
            mHandler = new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message inputMsg){
                    String msg = (String) inputMsg.obj;
                    Toast.makeText(MainActivity.this,
                            "map from executor "+msg,
                            Toast.LENGTH_SHORT).show();
                }
            };
        }

        @Override
        public void run() {
            Log.i("gg","generation started");
            Node node = new Node();
            Node[][] map = node.Generation(150);
            Log.i("gg", "generation completed");
            sendMsg(1, "Generated");
        }
        public void sendMsg(int what, String msg){
            Message message = mHandler.obtainMessage(what, msg);
            message.sendToTarget();
        }
    }
}