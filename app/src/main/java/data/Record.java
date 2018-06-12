package data;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;


//식물 정보 기록 DB 클래스 (메인)

public class Record {
    private UUID id; // 기록 id (DB식별용)
    private String name; //식물 애칭
    private String typename; //식물 종류
    private double light; //현 밝기값
    private double hume; //현 습도값
    private double temper; //현 온도값
    private double rotate; //회전값

    public Record(){

    }
    public Record(UUID id, String name, String typename, double light, double hume, double temper, double rotate){
        this.id=id;
        this.name=name;
        this.typename=typename;
        this.light=light;
        this.hume=hume;
        this.temper=temper;
        this.rotate=rotate;
    }


    public void delete(){ //정보 삭제
        if (id!=null){
            ;
        }

    }

}
