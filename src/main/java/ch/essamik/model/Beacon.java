package ch.essamik.model;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Model Beacon object with database attribute mapping
 */

@Entity
public class Beacon extends Base implements Serializable {

    private String vendorId;

    private String uuid;

    private int major;

    private int minor;

    //Default constructor for JPA
    public Beacon() {}

    public Beacon (Beacon b) {
        super(b);
        this.uuid = b.uuid;
        this.major = b.major;
        this.minor = b.minor;
        this.vendorId = b.vendorId;
    }

    public Beacon(String name, String uuid, int major, int minor) {
        super.setName(name);
        this.setUuid(uuid);
        this.setMajor(major);
        this.setMinor(minor);
    }

    public String getVendorId() {
        return this.vendorId;
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
