package com.resetframe.smartpotapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import data.drawerItems;



//recordfragment 참고
public class DrawerActivity extends Activity {
//1번 페이지. 화분 목록페이지.

    //private ArrayAdapter<Record> adapter; //<-나중에 부활

    int checked = -1;
    ArrayList <drawerItems> items;
    DrawerAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    //리스트 뷰 & 어댑터 초기화
    //}
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        items = new ArrayList<drawerItems>();

        //final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,items);

        adapter = new DrawerAdapter(this,items);

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
                int count;
                //int checked;
                count = adapter.getCount();

                if (count>0) {
                    checked = listview.getCheckedItemPosition();
                    Intent intent = new Intent(v.getContext(),PotInfoActivity.class);
                    //intent.putExtra("ID",UUID id);
                    //이게 최선입니까.

                    if (checked>=0) {
                        intent.putExtra("name", items.get(checked).getName());
                        intent.putExtra("typename", items.get(checked).getTypename());
                        intent.putExtra("date", items.get(checked).getDate());
                        intent.putExtra("light", items.get(checked).getLight());
                        intent.putExtra("hume", items.get(checked).getHume());
                        intent.putExtra("temp", items.get(checked).getTemper());

                        startActivityForResult(intent, 1);

                        System.out.println("count");
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        //--------------------
        Button deleteButton = (Button)findViewById(R.id.item_delete);
        deleteButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                int count;
                //int checked;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode!=resultCode){
            Toast.makeText(DrawerActivity.this,"갱신에 실패하였습니다.",Toast.LENGTH_SHORT).show();
            //return;
        }
        //items.get(count-1).setLight(data.getDoubleExtra("light",100));
        drawerItems temp_items = items.get(checked);
        items.set(checked,temp_items).setName(data.getStringExtra("name"));
        items.set(checked,temp_items).setTemper(99);
        items.set(checked,temp_items).setHume(99);
        items.set(checked,temp_items).setLight(99);

        System.out.println("!!"+items.get(checked).getTemper());
        adapter.reFresh(items);
        adapter.setText(checked);

        //"일단" 데이터는 갱신됨.

    }



}


