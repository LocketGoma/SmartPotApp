package com.resetframe.smartpotapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

//activity_pot
public class PotInfoActivity extends AppCompatActivity {
    private UUID id; // 기록 id (DB식별용)
    private String name; //식물 애칭
    private String typename; //식물 종류
    private String date;    //심은 일자
    private double light; //현 밝기값
    private double hume; //현 습도값
    private double temper; //현 온도값

    public PotInfoActivity(UUID id, String name, String typename, String date, double light, double hume, double temper) {
        this.id = id;
        this.name = name;
        this.typename = typename;
        this.date = date;
        this.light = light;
        this.hume = hume;
        this.temper = temper;
    }
    public PotInfoActivity(){
        ;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pot);

        Intent intent = getIntent();

        this.name = intent.getStringExtra("name");
        this.typename=intent.getStringExtra("typename");
        this.date=intent.getStringExtra("date");
        this.light=intent.getDoubleExtra("light",50.0);
        this.hume=intent.getDoubleExtra("hume",50.0);
        this.temper=intent.getDoubleExtra("temp",50.0);


    }
}
