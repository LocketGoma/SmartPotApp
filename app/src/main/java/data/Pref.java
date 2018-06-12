package data;

import android.content.Context;
import android.content.SharedPreferences;




/**
 * Created by LocketGoma on 2018-05-15.
 */
// 환경설정 클래스

public class Pref {
    public static boolean isFirst; // 이번 실행이 첫 번째 앱 실행인지 여부
    public static String gender;
    public static int height;
    public static int weight;

    public static void load(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences("Pref", Context.MODE_PRIVATE);
        isFirst = prefs.getBoolean("isFirst", true);

    }

    public static void save(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences("Pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isFirst", isFirst);
        editor.putString("gender", gender);
        editor.putInt("height", height);
        editor.putInt("weight", weight);


        editor.apply();
    }


}
