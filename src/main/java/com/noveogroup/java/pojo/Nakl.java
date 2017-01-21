package com.noveogroup.java.pojo;

import java.io.Serializable;
import java.util.Date;

import com.noveogroup.java.annotation.Getter;
import com.noveogroup.java.annotation.IntValue;
import com.noveogroup.java.annotation.NotFuture;
import com.noveogroup.java.annotation.NotNull;
import com.noveogroup.java.annotation.Setter;
import com.noveogroup.java.annotation.StringValue;


/**
 *
 * @author Alexander
 */
public class Nakl implements Serializable {

    @NotNull
    @StringValue(pattern = "[A-Za-z0-9-]+")
    private String name;

    @NotNull
    @StringValue(pattern = "[A-Za-z]+")
    private String good;

    @NotNull
    @IntValue(min = 0, max = 999999999)
    private int id;

    @NotNull
    @NotFuture
    private Date date;

    @Setter
    public void setName(String name) {
        this.name = name;
    }

    @Setter
    public void setGood(String good) {
        this.good = good;
    }

    @Setter
    public void setID(int id) {
        this.id = id;
    }

    @Setter
    public void setDate(Date date) {
        this.date = date;
    }

    @Getter
    public String getName() {
        return this.name;
    }

    @Getter
    public String getGood() {
        return this.good;
    }

    @Getter
    public int getID() {
        return this.id;
    }

    @Getter
    public Date getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return "Nakl{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", good='" + good + '\'' +
            ", date=" + date +
            '}';
    }
}
