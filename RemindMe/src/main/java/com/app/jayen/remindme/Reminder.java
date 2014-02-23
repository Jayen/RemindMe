package com.app.jayen.remindme;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class was created by Jayen on 22/02/14.
 */
public class Reminder implements Parcelable{
    private long id;
    private String title;
    private String description;
    private String location;
    private double lat;
    private double lng;

    public Reminder() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
         public String toString() {
        return title;
    }

    public Reminder(Parcel in) {
        String[] data = new String[6];
        in.readStringArray(data);
        this.id = Long.parseLong(data[0]);
        this.title = data[1];
        this.description =  data[2];
        this.location = data[3];
        this.lat = Double.parseDouble(data[4]);
        this.lng = Double.parseDouble(data[5]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{Long.toString(this.id),this.title,this.description, this.location,
                                Double.toString(this.lat),Double.toString(this.lng)});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
