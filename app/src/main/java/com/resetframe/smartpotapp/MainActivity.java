package com.resetframe.smartpotapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
//메인 액티비티는 관리용으로.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //이후 세팅은 필요할때 생성할것.

        startActivity(new Intent(this, DrawerActivity.class));
        //일단 바로 Drawer로 보내버리기.
    }
}
