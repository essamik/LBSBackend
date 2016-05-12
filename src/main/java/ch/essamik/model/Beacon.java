package ch.essamik.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Model Beacon object with database attribute mapping
 */

@Entity
public class Beacon implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String vendorId;

    private String name;

    private String uuid;

    private int major;

    private int minor;

    //Default constructor for JPA
    public Beacon() {}

    public Beacon (Beacon b) {
        this.id = b.id;
        this.name = b.name;
        this.uuid = b.uuid;
        this.major = b.major;
        this.minor = b.minor;
        this.vendorId = b.vendorId;
    }

    public Beacon(String name, String uuid, int major, int minor) {
        this.setName(name);
        this.setUuid(uuid);
        this.setMajor(major);
        this.setMinor(minor);
    }

    public Long getId() {
        return this.id;
    }

    public String getVendorId() {
        return this.vendorId;
    }

    public String getName() {
        return this.name;
    }

    public String getUuid() {
        return this.uuid;
    }

    public int getMajor() {
        return this.major;
    }

    public int getMinor() {
        return this.minor;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }
}
