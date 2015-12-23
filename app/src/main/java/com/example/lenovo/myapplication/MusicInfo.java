package com.example.lenovo.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lenovo on 2015/12/18.
 */
public class MusicInfo implements Parcelable{
    private int id;
    private int duration;
    private String title;
    private String artist;
    private String dataPath;
    private String displayName;
    private String sortDisplayName;
    private int index;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setSortDisplayName(String sortDisplayName) {
        this.sortDisplayName = sortDisplayName;
    }

    public String getSortDisplayName() {
        return sortDisplayName;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public MusicInfo(int id, String artist, String title, int duration, String dataPath, String displayName) {
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.duration = duration;
        this.dataPath = dataPath;
        this.displayName = displayName;
    }

    public MusicInfo(){

    }
    public MusicInfo(int id, String title, String dataPath, String artist, String displayName) {
        this.id = id;
        this.title = title;
        this.dataPath = dataPath;
        this.artist = artist;
        this.displayName = displayName;
        this.duration = duration;
    }

    protected MusicInfo(Parcel in) {
        id = in.readInt();
        title = in.readString();
        artist = in.readString();
        dataPath = in.readString();
        displayName = in.readString();
        duration = in.readInt();
    }

    public static final Creator<MusicInfo> CREATOR = new Creator<MusicInfo>() {
        @Override
        public MusicInfo createFromParcel(Parcel in) {
            return new MusicInfo(in);
        }

        @Override
        public MusicInfo[] newArray(int size) {
            return new MusicInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeString(dataPath);
        dest.writeString(displayName);
        dest.writeInt(duration);
    }
}
