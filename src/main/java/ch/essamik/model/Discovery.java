package ch.essamik.model;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Discovery implements Serializable {

    @Id
    @Field
    private String id;

    @Field
    private String emitterId;

    @Field
    private Date created;

    @Enumerated(EnumType.STRING)
    @Field
    private DiscoverEvent event;

    public enum DiscoverEvent {
        ENTER,
        EXIT,
        DWELL
    }

    public Discovery() {
    }

    public Discovery(String emitterId, DiscoverEvent event) {
        this.setEmitterId(emitterId);
        this.setEvent(event);
        this.id = UUID.randomUUID().toString();
        this.setCreated(new Date());
    }

    public String getId() {
        return this.id;
    }

    public String getEmitterId() {
        return emitterId;
    }

    public void setEmitterId(String emitterId) {
        this.emitterId = emitterId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        //Format the date for Solr
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date convertedDate = null;
        try {
            convertedDate = sdf.parse(sdf.format(created));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.created = convertedDate;
    }

    public DiscoverEvent getEvent() {
        return event;
    }

    public void setEvent(DiscoverEvent event) {
        this.event = event;
    }
}
