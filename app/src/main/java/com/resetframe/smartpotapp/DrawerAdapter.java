package com.resetframe.smartpotapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import data.drawerItems;

public class DrawerAdapter extends BaseAdapter {
    Context context;
    ArrayList<drawerItems> list_itemArrayList;
    //---
    TextView plantedDateView;     //심은일 뷰어
    TextView nameView;             //이름 뷰어
    TextView typenameView;        //타입 뷰어

    TextView temperPercentView;   //온도 뷰어
    TextView humedPercentView;    //습도 뷰어
    TextView lightPercentView;    //밝기 뷰어

    //---


    public DrawerAdapter(Context context, ArrayList<drawerItems> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

    @Override
    public int getCount() {
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.list_itemArrayList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_drawer,null);
            this.plantedDateView = (TextView)view.findViewById(R.id.txt_record_date);
            this.nameView = (TextView)view.findViewById(R.id.txt_record_name);
            this.typenameView = (TextView)view.findViewById(R.id.txt_record_type);
            this.temperPercentView = (TextView)view.findViewById(R.id.txt_record_temper);
            this.humedPercentView = (TextView)view.findViewById(R.id.txt_record_hume);
            this.lightPercentView = (TextView)view.findViewById(R.id.txt_record_light);

            plantedDateView.setText(list_itemArrayList.get(i).getDate());
            nameView.setText(list_itemArrayList.get(i).getName());
            typenameView.setText(list_itemArrayList.get(i).getTypename());
            temperPercentView.setText("온도"+String.valueOf(list_itemArrayList.get(i).getTemper())+"'c");
            humedPercentView.setText("습도"+String.valueOf(list_itemArrayList.get(i).getHume())+"%");
            lightPercentView.setText("밝기"+String.valueOf(list_itemArrayList.get(i).getLight())+"lx");


        }
        return view;
    }
}
