package data;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.resetframe.smartpotapp.R;

import org.w3c.dom.Text;

import java.util.UUID;

//Record에서 id에 맞춰서 값을 변환하여 drawer페이지에 뿌려주는 용도.

public class drawerItems extends LinearLayout{
    private UUID id; // 기록 id (DB식별용)
    private String name; //식물 애칭
    private String typename; //식물 종류
    private double light; //현 밝기값
    private double hume; //현 습도값
    private double temper; //현 온도값

    TextView plantedDateView;     //심은일 뷰어
    TextView nameView;             //이름 뷰어
    TextView typenameView;        //타입 뷰어
    TextView lightPercentView;    //밝기 뷰어
    TextView humedPercentView;    //습도 뷰어
    TextView temperPercentView;   //온도 뷰어

    public drawerItems(Context context, UUID id, String name, String typename, double light, double hume, double temper) {
        super(context);
        this.id = id;
        this.name = name;
        this.typename = typename;
        this.light = light;
        this.hume = hume;
        this.temper = temper;

        plantedDateView = (TextView) findViewById(R.id.txt_record_date);
        nameView = (TextView) findViewById(R.id.txt_record_name);
        typenameView = (TextView) findViewById(R.id.txt_record_type);
        lightPercentView = (TextView) findViewById(R.id.txt_record_light);
        humedPercentView = (TextView) findViewById(R.id.txt_record_hume);
        temperPercentView = (TextView) findViewById(R.id.txt_record_temper);

        this.setTypenameView(typename);
        this.setHumedPercentView(hume);
        this.setLightPercentView(light);
        this.setNameView(name);
        this.setPlantedDateView("0000-00-00");
        this.setTemperPercentView(temper);


    }
    private void inflation_init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_drawer,this,true);
    }

    public void setPlantedDateView(String date) {
        plantedDateView.setText(date);
    }

    public void setNameView(String name) {
        nameView.setText(name);
    }

    public void setTypenameView(String typename) {
        typenameView.setText(name);
    }

    public void setLightPercentView(double light) {
        lightPercentView.setText((int)light);
    }

    public void setHumedPercentView(double hume) {
        humedPercentView.setText((int)hume);
    }

    public void setTemperPercentView(double temper) {
        temperPercentView.setText((int)temper);
    }
}
