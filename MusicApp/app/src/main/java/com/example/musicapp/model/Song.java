package com.example.musicapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "song")
public class Song implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    private int rscId;

    protected Song(Parcel in) {
        id = in.readInt();
        name = in.readString();
        rscId = in.readInt();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Song(String name, int rscId) {
        this.name = name;
        this.rscId = rscId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRscId() {
        return rscId;
    }

    public void setRscId(int rscId) {
        this.rscId = rscId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(rscId);
    }
}
