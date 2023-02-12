package de.jonashackt.springbootvuejs.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ratio")
public class Ratio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double ratio;
    private Integer torrentId;
    private Date dateTime;

    Ratio() {}

    public Ratio(double ratio, Integer torrentId, Date dateTime) {
        this.ratio = ratio;
        this.torrentId = torrentId;
        this.dateTime = dateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public Integer getTorrentId() {
        return torrentId;
    }

    public void setTorrentId(Integer torrentId) {
        this.torrentId = torrentId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Ratio{" +
                "id=" + id +
                ", ratio=" + ratio +
                ", torrentId=" + torrentId +
                ", dateTime=" + dateTime +
                '}';
    }
}
