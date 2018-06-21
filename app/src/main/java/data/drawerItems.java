package data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.resetframe.smartpotapp.R;

import java.util.UUID;

//Record에서 id에 맞춰서 값을 변환하여 drawer페이지에 뿌려주는 용도.

public class drawerItems extends LinearLayout{
    private UUID id; // 기록 id (DB식별용)
    private String name; //식물 애칭
    private String typename; //식물 종류
    private String date;    //심은 일자
    private double light; //현 밝기값
    private double hume; //현 습도값
    private double temper; //현 온도값

    public drawerItems(Context context){
        super(context);

        name = "DefaultName";
        date = "0000-00-00";
        light = 50;
        hume = 50;
        temper = 50;

    }

    public drawerItems(Context context, UUID id, String name, String typename, double light, double hume, double temper) {
        super(context);
        this.id = id;
        this.name = name;
        this.typename = typename;
        this.light = light;
        this.hume = hume;
        this.temper = temper;
    }
    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTypename() {
        return typename;
    }

    public double getLight() {
        return light;
    }

    public double getHume() {
        return hume;
    }

    public double getTemper() {
        return temper;
    }
}
