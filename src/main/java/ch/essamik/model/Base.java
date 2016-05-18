package ch.essamik.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Base {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Base (Base b) {
        this.id = b.id;
        this.name = b.name;
    }

    public Base() {

    }


    public Long getId() {
        return this.id;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

