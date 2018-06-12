package com.resetframe.smartpotapp;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import data.Record;

//recordfragment 참고
public class DrawerActivity extends AppCompatActivity /*implements AdapterView.OnItemClickListener*/ {
//1번 페이지. 화분 목록페이지.

    //private ArrayAdapter<Record> adapter; //<-나중에 부활
    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"} ;

    @RequiresApi(api = Build.VERSION_CODES.M)
    //public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup containder, @Nullable Bundle savedInstanceState){
    //리스트 뷰 & 어댑터 초기화
    //}
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //adapter = new ArrayAdapter<Record>(getContext(), R.layout.item_drawer);        //item_drawer = 아이템 레코드. 나중에 부활
        setContentView(R.layout.activity_drawer);

        //2
        final ArrayList<String> items = new ArrayList<String>();

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items) ;

        final ListView listview = (ListView) findViewById(R.id.item_drawer) ;
        listview.setAdapter(adapter) ;
        //1
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get TextView's Text.
                String strText = (String) parent.getItemAtPosition(position);
            }
        });
        //--------------------
        Button addButton = (Button)findViewById(R.id.item_add);
        addButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                int count;
                count = adapter.getCount();

                items.add("LIST"+Integer.toString(count+1));
                adapter.notifyDataSetChanged();
            }
        });
        //--------------------
        Button modifyButton = (Button)findViewById(R.id.item_modify);
        modifyButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                int count, checked;
                count = adapter.getCount();

                if (count>0) {
                    checked = listview.getCheckedItemPosition();
                    if (checked > -1 && checked < count) {
                        items.set(checked,Integer.toString(count + 1)+"번 아이템 수정");
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        //--------------------
        Button deleteButton = (Button)findViewById(R.id.item_delete);
        deleteButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                int count, checked;
                count = adapter.getCount();


                if (count>0) {
                    checked = listview.getCheckedItemPosition();
                    if (checked > -1 && checked < count) {
                        items.remove(checked);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        //--------------------


    }


}



/*
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ;
    }
    */

