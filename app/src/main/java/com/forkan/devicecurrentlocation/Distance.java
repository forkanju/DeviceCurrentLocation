package com.forkan.devicecurrentlocation;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

//Distance is a model calss
public class Distance extends RealmObject {

    @PrimaryKey
    public String id = UUID.randomUUID().toString();
    public double pLatitude;
    public double pLongitude;
    public long distance;
    public long timestamp;

    public Distance() {
    }

    public Distance(double pLatitude, double pLongitude, long distance, long timestamp) {
        this.pLatitude = pLatitude;
        this.pLongitude = pLongitude;
        this.distance = distance;
        this.timestamp = timestamp;
    }

    public double getpLatitude() {
        return pLatitude;
    }

    public double getpLongitude() {
        return pLongitude;
    }

    public long getDistance() {
        return distance;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
