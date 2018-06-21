package com.example.resetframe.sandbox005;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction("action.ACTION_IMAGE_VIEW");
        Uri imageUri = Uri.parse (
                "http://www.superdroid.com:80/files/images/test.png?a=0#3");
        intent.setDataAndType(imageUri, "image/png");
        startActivity(intent);
    }

/*
    void onClick(View v){
// 1. 인텐트를 하나 생성한다.
// ====================================================================
        Intent intent = new Intent();
// ====================================================================
// 2. 인텐트에 실행할 패키지의 액티비티 정보를 설정한다.
// ====================================================================
        ComponentName componentName = new ComponentName (
                "com.example.resetframe.sandbox005_3",
                "com.example.resetframe.sandbox005_3.MainActivity");
        intent.setComponent(componentName);
// ====================================================================
// 3. Bundle 객체를 생성 및 데이터를 추가하고 실행될 액티비티에게 전달할
// 인텐트에 추가한다.
// ====================================================================
        Bundle bundleData = new Bundle();
        bundleData.putInt("INT_DATA", 123456789);
        bundleData.putString("STR_DATA", "Bundle String");
        intent.putExtra("SAMPLE_DATA", bundleData);
// ====================================================================
// 4. B 앱의 액티비티를 실행한다.
// ====================================================================
        startActivity(intent);
// ====================================================================
    }
*/
    void reSpawn(View v){
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName (
                    "com.example.resetframe.sandbox005",
                    "com.example.resetframe.sandbox005.A2Activity");
            intent.setComponent(componentName);
            startActivity(intent);
    }

    void reCall(View v){
        // 1. 인텐트를 하나 생성한다.
// ====================================================================
        Intent intent = new Intent();
// ====================================================================
// 2. 외부 이메일 컴포넌트를 요청한다.
// ====================================================================
// ① 단말기에 설치된 앱을 실행했을 때 가장 첫 번째로 실행되는 액티비티를 보여달라는 액션
        intent.setAction(Intent.ACTION_MAIN);
// ② 안드로이드 기본 앱 중 이메일 카테고리
        intent.addCategory(Intent.CATEGORY_APP_EMAIL);
// ====================================================================
// 3. 외부 컴포넌트를 실행한다.
// ====================================================================
        startActivity(intent);
// ====================================================================
    }

 }

