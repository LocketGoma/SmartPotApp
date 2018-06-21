package com.resetframe.smartpotapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by LocketGoma on 2018-05-15.
 */
// 처음이 아니면 건너뜀.

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_1);
        /*
        if (!Pref.isFirst) { //처음 실행이 아닐때 바로 메인액티비티로 점프.
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        */
        System.out.println("hello");
        startActivity(new Intent(this, MainActivity.class));
/*
        @Override
        public Object instantiateItem(ViewGroup container, int postion) {
            View root = null;
            LayoutInflater inflater = getLayoutInflater();

            switch (postion) {
                case 0:
                    root = inflater.inflate(R.layout.start_1, null);
            }

               return null;
        }
        */
    }

}
