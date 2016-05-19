package ch.essamik.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Geofence implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;
    private double latitude;
    private double longitude;
    private float radius;

    public Geofence(Geofence geofence){
        this.name = geofence.name;
        this.latitude = geofence.latitude;
        this.longitude = geofence.longitude;
        this.radius = geofence.radius;
    }

    public Geofence(String name, double latitude, double longitude, float radius) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    //Default constructor for JPA
    public Geofence() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
