package com.wg.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import com.wg.news.R;

/**
 * Created by EXP on 2015/8/25.
 */
public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_layout);
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            }
        }.start();

    }
}
