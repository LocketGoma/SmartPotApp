package com.resetframe.smartpotapp;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.database.DataSetObserver;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import data.Record;
import data.drawerItems;



//recordfragment 참고
public class DrawerActivity extends AppCompatActivity {
//1번 페이지. 화분 목록페이지.

    //private ArrayAdapter<Record> adapter; //<-나중에 부활



    @RequiresApi(api = Build.VERSION_CODES.M)
    //리스트 뷰 & 어댑터 초기화
    //}
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        final ArrayList <drawerItems> items = new ArrayList<drawerItems>();

        //final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,items);

        final DrawerAdapter adapter = new DrawerAdapter(this,items);

        final ListView listview = (ListView) findViewById(R.id.item_drawer) ;
        listview.setAdapter(adapter);
        //1

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get TextView's Text.
                drawerItems selected = (drawerItems) parent.getItemAtPosition(position);
            }
        });

        //--------------------
        Button addButton = (Button)findViewById(R.id.item_add);
        addButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                int count;
                count = adapter.getCount();

                drawerItems itemView = new drawerItems(getApplicationContext());
                //System.out.println("printed::"+itemView.nameView.getText());
                items.add(itemView);
                //items.add("LIST"+count+1);
                adapter.notifyDataSetChanged();
            }
        });
        //--------------------
        Button detailButton = (Button)findViewById(R.id.item_detail);
        detailButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                int count, checked;
                count = adapter.getCount();



                if (count>0) {
                    checked = listview.getCheckedItemPosition();
                    Intent intent = new Intent(v.getContext(),PotInfoActivity.class);
                    //intent.putExtra("ID",UUID id);
                    //이게 최선입니까.
                    intent.putExtra("name", items.get(count-1).getName());
                    intent.putExtra("typename",items.get(count-1).getTypename());
                    intent.putExtra("date",items.get(count-1).getDate());
                    intent.putExtra("light",items.get(count-1).getLight());
                    intent.putExtra("hume",items.get(count-1).getHume());
                    intent.putExtra("temp",items.get(count-1).getTemper());

                    startActivity(intent);

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


