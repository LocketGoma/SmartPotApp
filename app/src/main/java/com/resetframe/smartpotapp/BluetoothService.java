package com.resetframe.smartpotapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import static com.resetframe.smartpotapp.PotControlActivity.dataText;

public class BluetoothService {
    private static final String TAG = "BluetoothService";

    //블루투스 상태 확인
    public static final int NOT_SUPPORT_BLUETOOTH = -1; //블루투스 지원안함
    public static final int BLUETOOTH_ENABLE = 0;       //블루투스 사용가능
    public static final int REQUEST_BLUETOOTH_ENABLE = 1;//블루투스 전원꺼짐

    //블루투스 연결 확인용 어댑터
    public static BluetoothAdapter bluetoothAdapter;



    //블루트스 연결
    private BTConnection connectionTask;
    //블루투스 소켓
    private BTSocket socketTask;
    //블루투스 디바이스
    private BluetoothDevice bluetoothDevice;

    public BluetoothService(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    //블루투스 연결
    public void bluetoothConnect(BluetoothDevice bluetoothDevice){
        connectionTask = new BTConnection(bluetoothDevice);
        connectionTask.execute();
    }

    //블루투스 지원 및 활성화 확인
    public int checkConnectAvailable(){
        if(bluetoothAdapter == null){ //이 기기가 블루투스를 지원하지 않음
            return NOT_SUPPORT_BLUETOOTH;
        }
        else{
            if(bluetoothAdapter.isEnabled()){// 이 기기의 블루투스가 활성화 되어있음
                return BLUETOOTH_ENABLE;
            }
            else return REQUEST_BLUETOOTH_ENABLE; // 이 기기의 블루투스가 활성화 되어있지 않음
        }
    }

    public boolean sendMessage(int mode, double... args) {      //기본형 : boolean
        if (socketTask != null) {
            StringBuilder sb = new StringBuilder("");
            sb.append(mode);
            sb.append(">");
            for (double value : args) {
                sb.append(value);
                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("<");
            String msg = sb.toString();

            if(socketTask.write(msg)) {
                System.out.println("SENT : " + msg);
                Log.d(TAG, "send message: " + msg);
                return true;
            }else return false;
        } else {
            return false;
        }
    }
 public double[] getMessage(int mode, double... args) {      //데이터 받아오기
        double [] result = new double[3];
        int i = 0;
        if (socketTask != null) {
            StringBuilder sb = new StringBuilder("");
            sb.append(mode);
            sb.append(">");
            for (double value : args) {
                sb.append(value);
                sb.append(",");
                result[i++] = value;
                System.out.println(result[0]);
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("<");
            String msg = sb.toString();

            if(socketTask.write(msg)) {
                System.out.println("SENT : " + msg);
                Log.d(TAG, "send message: " + msg);
                return result;
            }else return null;
        } else {
            return null;
        }
    }


    //블루투스 연결 스레드
    public class  BTConnection extends AsyncTask<Void, Void, Boolean> {
        //임베디드 UUID는 고정
        private static final String emUUID = "00001101-0000-1000-8000-00805f9b34fb";
        private BluetoothSocket mBluetoothSocket = null;
        private String mConnectedDeviceName;

        private BTConnection(BluetoothDevice bluetoothDevice) {
            BluetoothService.this.bluetoothDevice = bluetoothDevice;
            mConnectedDeviceName = bluetoothDevice.getName();
            UUID uuid = UUID.fromString(emUUID);
            try {
                mBluetoothSocket = BluetoothService.this.bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
                Log.d(TAG, "create socketTask for " + mConnectedDeviceName);
            } catch (IOException e) {
                Log.e(TAG, "socketTask create failed " + e.getMessage());
            }
            System.out.println("connecting...");
        }

        //소켓에 연결시도
        private void connectTo( BluetoothSocket blueSocket ) {
            socketTask = new BTSocket(blueSocket);
            socketTask.execute();
        }

        //작업이 시작되기 전 실행
        @Override
        protected void onPostExecute(Boolean isSuccess) {
            if ( isSuccess ) {
                connectTo(mBluetoothSocket);
                System.out.println("connectTo : " + bluetoothDevice.getName());
            }
            else{
                /**사용자에게 알려야 함***/
                Log.d( TAG,  "Unable to connect device");
            }
        }

        //작업 실행
        @Override
        protected Boolean doInBackground(Void... voids) {
            // 블루투스 검색 중지(else 연결 속도 늦어짐)
            bluetoothAdapter.cancelDiscovery();
            // 블루투스와 연결
            try {
                mBluetoothSocket.connect();
            } catch (IOException e) { //연결실패
                try {
                    //소켓 닫음
                    mBluetoothSocket.close();
                } catch (IOException e2) {    //소켓 닫기 실패
                    Log.e(TAG, "unable to close() " +
                            " socketTask during connectionTask failure", e2);
                }
                return false;
            }
            return true;
        }
    }

    //블루투스 소켓 스레드
    public class BTSocket extends AsyncTask<Void, String, Boolean> {
        //명령어 구분자
        private static final String MODE_DELIM = ">";

        private InputStream inputStream;
        private OutputStream outputStream;

        public boolean isOK; //FALSE

        private BluetoothSocket BTSocket;

        BTSocket(BluetoothSocket socket) {
            BTSocket = socket;
            try {
                inputStream = BTSocket.getInputStream();
                outputStream = BTSocket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "socketTask not created", e);
            }

            Log.d(TAG, "connected to " + bluetoothDevice.getName());
            isOK = true;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            byte[] readBuffer = new byte[1024];
            int readBufferPosition = 0;


            while (true) {

                if (isCancelled()) return false;

                try {

                    int bytesAvailable = inputStream.available();

                    if (bytesAvailable > 0) {

                        byte[] packetBytes = new byte[bytesAvailable];

                        inputStream.read(packetBytes);

                        for (int i = 0; i < bytesAvailable; i++) {

                            byte b = packetBytes[i];
                            if (b == '<') {
                                byte[] encodedBytes = new byte[readBufferPosition];
                                System.arraycopy(readBuffer, 0, encodedBytes, 0,
                                        encodedBytes.length);
                                String recvMessage = new String(encodedBytes, "UTF-8");

                                readBufferPosition = 0;

                                Log.d(TAG, "recv message: " + recvMessage);
                                publishProgress(recvMessage);
                            } else {
                                readBuffer[readBufferPosition++] = b;
                            }
                        }
                    }
                } catch (IOException e) {

                    Log.e(TAG, "disconnected", e);
                    return false;
                }
            }

        }

        @Override
        protected void onProgressUpdate(String... recvMessage) {
            if(dataText != null)
                dataText.setText(recvMessage[0]);
        }

        @Override
        protected void onPostExecute(Boolean isSucess) {
            super.onPostExecute(isSucess);
            if (!isSucess) {
                closeSocket();
                Log.d(TAG, "Device connectionTask was lost");
            }
        }

        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);

            closeSocket();
        }

        void closeSocket() {

            try {

                BTSocket.close();
                Log.d(TAG, "close socketTask()");

            } catch (IOException e2) {

                Log.e(TAG, "unable to close() " +
                        " socketTask during connectionTask failure", e2);
            }
        }

        private boolean write(String msg) {
            //msg += "\n";
            try {
                outputStream.write(msg.getBytes());
                outputStream.flush();
                return true;
            } catch (IOException e) {
                System.out.println("write err : " + e.toString());
                Log.e(TAG, "Exception during send", e);
                return false;
            }
        }


    }

    public BTConnection getConnectionTask() {
        return connectionTask;
    }
}

