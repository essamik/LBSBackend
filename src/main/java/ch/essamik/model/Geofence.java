package ch.essamik.model;


import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Geofence extends Base implements Serializable{

    private double latitude;
    private double longitude;
    private float radius;

    public Geofence(Geofence geofence){
        this.latitude = geofence.latitude;
        this.longitude = geofence.longitude;
        this.radius = geofence.radius;
    }

    public Geofence(String name, double latitude, double longitude, float radius) {
        super.setName(name);
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    //Default constructor for JPA
    public Geofence() {}

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
