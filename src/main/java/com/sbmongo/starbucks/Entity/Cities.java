package com.sbmongo.starbucks.Entity;

import org.springframework.data.annotation.Id;

import java.util.Arrays;

public class Cities {
    @Id
    private String _id;
    private String city;
    private String[] loc;
    private String pop;
    private String state;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    String getLocation() {
        return loc[0] + " " + loc[1];
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String[] getLoc() {
        return loc;
    }

    public void setLoc(String[] loc) {
        this.loc = loc;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Cities{" +
                "_id='" + _id + '\'' +
                ", city='" + city + '\'' +
                ", loc=" + Arrays.toString(loc) +
                ", pop='" + pop + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
