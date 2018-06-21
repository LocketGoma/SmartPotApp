package com.resetframe.smartpotapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.UUID;

//activity_pot
public class PotInfoActivity extends Activity {
    private UUID id; // 기록 id (DB식별용)
    private String name; //식물 애칭
    private String typename; //식물 종류
    private String date;    //심은 일자
    private double temper; //현 온도값
    private double hume; //현 습도값
    private double light; //현 밝기값

    TextView temperPercentView;   //온도 뷰어
    TextView humedPercentView;    //습도 뷰어
    TextView lightPercentView;    //밝기 뷰어


    ImageView temperPercentImg;
    ImageView humedPercentImg;
    ImageView lightPercentImg;

    float density;

    Uri mlmageCaptureUri;
    ImageView iv_UserPhoto;

    public PotInfoActivity(UUID id, String name, String typename, String date, double light, double hume, double temper) {
        this.id = id;
        this.name = name;
        this.typename = typename;
        this.date = date;


        this.temper = temper;
        this.hume = hume;
        this.light = light;
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
        this.temper=intent.getDoubleExtra("temp",1.1);
        this.hume=intent.getDoubleExtra("hume",1.1);
        this.light=intent.getDoubleExtra("light",1.1);


        //View view= LayoutInflater.from(this).inflate(R.layout.activity_pot,null);
        this.temperPercentView = (TextView)findViewById(R.id.image_pot_info1);
        this.humedPercentView = (TextView)findViewById(R.id.image_pot_info2);
        this.lightPercentView = (TextView)findViewById(R.id.image_pot_info3);

        this.lightPercentImg = (ImageView)findViewById(R.id.pot_light_bar);
        this.humedPercentImg = (ImageView)findViewById(R.id.pot_humed_bar);
        this.temperPercentImg = (ImageView)findViewById(R.id.pot_temp_bar);

        Glide.with(this).load(R.drawable.bar_light).into(lightPercentImg);
        Glide.with(this).load(R.drawable.bar_humid).into(humedPercentImg);
        Glide.with(this).load(R.drawable.bar_temp).into(temperPercentImg);

        temperPercentView.setText(String.valueOf(temper));
        humedPercentView.setText(String.valueOf(hume));
        lightPercentView.setText(String.valueOf(light));

        Button configButton = (Button) findViewById(R.id.btn__config);
        configButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                Intent config_intent = new Intent(v.getContext(), PotControlActivity.class);
                config_intent.putExtra("temp",temper);
                config_intent.putExtra("hume",hume);
                config_intent.putExtra("light",light);
                startActivityForResult(config_intent,3);
            }
        });
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        density =  outMetrics.density;
        this.imageRefresh(330*density);

        iv_UserPhoto=(ImageView)findViewById(R.id.image_record_pot);

    }
    private void imageRefresh(double Length){
        double temp_max = 100;          //온도 퍼센트 관련
        double temp_min = -100;
        double temp_percent = 50;
        double hume_max = 100;          //습도 퍼센트 관련
        double hume_min = 0;
        double hume_percent = 50;
        double light_max = 1000;        //조도 퍼센트 관련
        double light_min = 0;
        double light_percent = 50;

        temp_percent = temper/(temp_max-temp_min)*100;
        hume_percent = hume/(hume_max-hume_min)*100;
        light_percent = light/(light_max-light_min)*100;

        //System.out.println("!!:"+Length+"::"+light_percent/100+"::"+light_percent/100*Length);

        LinearLayout.LayoutParams temp_params = (LinearLayout.LayoutParams) temperPercentImg.getLayoutParams();
        temp_params.width = (int)(temp_percent/100*Length);
        temperPercentImg.setLayoutParams(temp_params);

        LinearLayout.LayoutParams hume_params = (LinearLayout.LayoutParams) humedPercentImg.getLayoutParams();
        hume_params.width = (int)(hume_percent/100*Length);
        humedPercentImg.setLayoutParams(hume_params);

        LinearLayout.LayoutParams light_params = (LinearLayout.LayoutParams) lightPercentImg.getLayoutParams();
        light_params.width = (int)(light_percent/100*Length);
        lightPercentImg.setLayoutParams(light_params);


        //System.out.println("!!!:"+light_params.width);
    }
    private boolean reFresh(double [] input){
        if (temper==input[0]||hume==input[1]||light==input[2])
            return false;

        this.temper=input[0];
        this.hume=input[1];
        this.light=input[2];

        temperPercentView.setText(String.valueOf(temper));
        humedPercentView.setText(String.valueOf(hume));
        lightPercentView.setText(String.valueOf(light));

        return true;
    }
    private void pushData(){
        Intent output_intent = new Intent();

        output_intent.putExtra("temper",this.temper);
        output_intent.putExtra("hume",this.hume);
        output_intent.putExtra("light",this.light);
        output_intent.putExtra("name",this.name);

        setResult(1,output_intent);
    }

    public void onClick(View v){
        //int id_view = v.getId();
        if (v.getId()==R.id.image_record_pot) {
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    String url = "temp_"+String.valueOf(System.currentTimeMillis())+".jpg";
                    //mlmageCaptureUri =  Uri.fromFile(new File(Environment.getExternalStorageDirectory(),url));

                    @NonNull
                    File file = new File(Environment.getExternalStorageDirectory(),url);

                    mlmageCaptureUri = FileProvider.getUriForFile(PotInfoActivity.this,"com.resetframe.smartpotapp.fileprovider",file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mlmageCaptureUri);
                    startActivityForResult(intent,11);
                }
            };
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    startActivityForResult(intent,12);
                }
            };
            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            };

            new AlertDialog.Builder(this)
                    .setTitle("업로드 할 이미지 선택")
                    .setPositiveButton("촬영하기", cameraListener)
                    .setNeutralButton("앨범선택", albumListener)
                    .setNegativeButton("취소", cancelListener)
                    .show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            Toast.makeText(PotInfoActivity.this,
                    "화분 데이터를 갱신하지 못했습니다.", Toast.LENGTH_SHORT).show();
        } else if (resultCode == 3) {
            double[] output = new double[3];
            output[0] = data.getDoubleExtra("temper", temper);
            output[1] = data.getDoubleExtra("hume", hume);
            output[2] = data.getDoubleExtra("light", light);


            System.out.println("!!" + output[0]);
            if (!reFresh(output))
                Toast.makeText(PotInfoActivity.this, "화분 데이터 갱신에 문제가 생겼을 가능성이 있습니다. \n 재시도 해주세요.", Toast.LENGTH_LONG).show();
            this.imageRefresh(330 * density);
        } else if (resultCode == 4) {
            this.name = data.getStringExtra("name");
            Toast.makeText(PotInfoActivity.this, name, Toast.LENGTH_SHORT).show();

        } else if (requestCode == 11) {//camera
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(mlmageCaptureUri, "image/*");
            intent.putExtra("outputX", 550); // CROP한 이미지의 x축 크기
            intent.putExtra("outputY", 550); // CROP한 이미지의 y축 크기
            intent.putExtra("aspectX", 1); // CROP 박스의 X축 비율
            intent.putExtra("aspectY", 1); // CROP 박스의 Y축 비율
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 13); //

        } else if (requestCode == 12) {//album
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(data.getData(), "image/*");
            intent.putExtra("outputX", 550); // CROP한 이미지의 x축 크기
            intent.putExtra("outputY", 550); // CROP한 이미지의 y축 크기
            intent.putExtra("aspectX", 1); // CROP 박스의 X축 비율
            intent.putExtra("aspectY", 1); // CROP 박스의 Y축 비율
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 13); //

        } else if (requestCode == 13) {//crop
            {
                final Bundle extras = data.getExtras();
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/SmartPot/" + System.currentTimeMillis() + ".jpg";
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data"); // CROP된 이미지
                    iv_UserPhoto.setImageBitmap(photo); // Crop된 이미지를 보여줌.
                    //storeCropImage(photo, filePath); // CROP된 이미지를 외부저장소, 앨범에 저장한다.
                    //absoultePath = filePath;
                }
                //아래는 이미지 저장파트. http://jeongchul.tistory.com/287 참조
                /*
                File f = new File(mlmageCaptureUri.getPath());
                if(f.exists())
                {
                    f.delete();
                }
                */
            }
            //이미지 저장하려면 Bitmap을 넘겨주면 되는데...
        }

        this.pushData();

    }





}
