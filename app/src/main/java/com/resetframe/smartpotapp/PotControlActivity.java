package com.resetframe.smartpotapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

//activity_potcontrol (potinfo 확장)
public class PotControlActivity extends Activity {
    public static final String TAG = "PotControlActivity";

    private Button sendT, sendH, sendL, sendA, getA, getName;
    private EditText inputT, inputH, inputL;
    public static TextView dataText;

    private BluetoothService blueService;

    private static int MODE_SET_DATA1 = 101;
    private static int MODE_SET_DATA3 = 103;
    private static int MODE_GET_DATA = 100;

    //-----------------
    private double light; //설정된 밝기값
    private double hume; //설정된 습도값
    private double temper; //설정된 온도값

    //-----------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__control);

        //-----
        Intent input_intent = getIntent();

        //-----

        sendT = findViewById(R.id.sendT);
        sendH = findViewById(R.id.sendH);
        sendL = findViewById(R.id.sendL);
        //sendA = findViewById(R.id.sendA);
        getA = findViewById(R.id.getA);
        inputT = findViewById(R.id.tempInput);
        inputH = findViewById(R.id.humiInput);
        inputL = findViewById(R.id.lightInput);
        dataText = findViewById(R.id.dataText);
        getName = findViewById(R.id.name_button);

        blueService = new BluetoothService();

        sendT.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String msg = inputT.getText().toString();
                if ( msg.length() > 0 ) {
                    double value = Double.parseDouble(msg);
                    blueService.sendMessage(MODE_SET_DATA1,0, value, value+10);
                }
            }
        });
        sendH.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String msg = inputH.getText().toString();
                if ( msg.length() > 0 ) {
                    double value = Double.parseDouble(msg);
                    blueService.sendMessage(MODE_SET_DATA1,1, value, value+10);
                }
            }
        });
        sendL.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String msg = inputL.getText().toString();
                if ( msg.length() > 0 ) {
                    double value = Double.parseDouble(msg);
                    blueService.sendMessage(MODE_SET_DATA1,2, value, value+10);
                }
            }
        });
        /*
        sendA.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String msg1 = inputT.getText().toString();
                String msg2 = inputH.getText().toString();
                String msg3 = inputL.getText().toString();
                if ( msg1.length() > 0 && msg3.length() > 0 && msg2.length() > 0 ) {
                    double value1 = Double.parseDouble(msg1);
                    double value2 = Double.parseDouble(msg2);
                    double value3 = Double.parseDouble(msg3);
                    blueService.sendMessage(MODE_SET_DATA3,value1,value1+10, value2,value2+10,
                            value3,value3 + 10);
                }
            }
        });
        */

        getA.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                double [] result;
                result=blueService.getMessage(MODE_GET_DATA);
                if (result==null){
                    Toast.makeText(PotControlActivity.this,
                            "데이터 획득에 실패하였습니다.", Toast.LENGTH_SHORT).show();

                    //-- 실제로는 지울 부분
                    Intent output_intent = new Intent();
                    output_intent.putExtra("temper",(double)20.0);
                    output_intent.putExtra("hume",(double)50.0);
                    output_intent.putExtra("light",(double)100.0);

                   setResult(3,output_intent);
                   //---
                }
                else{
                    Intent output_intent = new Intent();
                    output_intent.putExtra("temper",result[0]);
                    output_intent.putExtra("hume",result[1]);
                    output_intent.putExtra("light",result[2]);
                    System.out.println(result[0]);
                    Toast.makeText(PotControlActivity.this,result[0]+":"+result[1]+":"+result[2],Toast.LENGTH_LONG).show();
                    setResult(3,output_intent);
                }
            }
        });

        getName.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText editName = (EditText)findViewById(R.id.edit_name);
                System.out.println("name:"+editName.getText());
                Intent output_intent = new Intent();
                output_intent.putExtra("name",editName.getText().toString());


                setResult(4,output_intent);
            }
        });














        //블루투스 연결상태 확인
        int result = blueService.checkConnectAvailable();
        if(result == BluetoothService.NOT_SUPPORT_BLUETOOTH) {
            Toast.makeText(PotControlActivity.this,
                    "기기가 블루투스를 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
        }else if(result == BluetoothService.BLUETOOTH_ENABLE){
            Intent intent = new Intent(blueService.bluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothService.REQUEST_BLUETOOTH_ENABLE);
            showPairedDevicesListDialog();

        }else if(result == BluetoothService.REQUEST_BLUETOOTH_ENABLE){
            Toast.makeText(PotControlActivity.this,
                    "블루투스를 켜주세요", Toast.LENGTH_SHORT).show();
        }

    }


    public void showPairedDevicesListDialog()
    {

        Set<BluetoothDevice> devices = blueService.bluetoothAdapter.getBondedDevices();
        final BluetoothDevice[] pairedDevices = devices.toArray(new BluetoothDevice[0]);

        if ( pairedDevices.length == 0 ){
            Toast.makeText(PotControlActivity.this,
                    "페어링된 기록이 없습니다", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] items;
        items = new String[pairedDevices.length];
        for (int i=0;i<pairedDevices.length;i++) {
            items[i] = pairedDevices[i].getName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select device");
        builder.setCancelable(false);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                blueService.bluetoothConnect(pairedDevices[which]);
            }
        });
        builder.create().show();
    }

    protected void onDestroy() {
        super.onDestroy();
        if ( blueService.getConnectionTask() != null ) {

            blueService.getConnectionTask().cancel(true);
        }
    }


}