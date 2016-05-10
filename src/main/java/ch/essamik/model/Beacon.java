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

    public Beacon(String name, String uuid, int major, int minor) {
        this.name = name;
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
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
}
