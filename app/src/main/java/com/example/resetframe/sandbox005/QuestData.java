package com.example.resetframe.sandbox005;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by ResetFrame on 2016-11-17.
 */

//public class QuestData implements Serializable {
public class QuestData implements Parcelable {
    private static final long serialVersionUID = 1000000L;
    private int mIntData = 0;
    private String mStrData = "Superdroid";

    protected QuestData(Parcel in) {
        mIntData = in.readInt();
        mStrData = in.readString();
    }
    public QuestData() {
        ;
    }

    public static final Creator<QuestData> CREATOR = new Creator<QuestData>() {
        @Override
        public QuestData createFromParcel(Parcel in) {
            return new QuestData(in);
        }

        @Override
        public QuestData[] newArray(int size) {
            return new QuestData[size];
        }
    };

    public int getIntData() {
        return mIntData;
    }

    public String getStringData() {
        return mStrData;
    }

    public void setIntData(int intData) {
        mIntData = intData;
    }

    public void setStringData(String strData) {
        mStrData = strData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIntData);
        dest.writeString(mStrData);
    }
}
