package com.noveogroup.java.pojo;

import java.io.Serializable;
import com.noveogroup.java.annotation.*;

/**
 *
 * @author Alexander
 */
public class Producer implements Serializable {

    @NotNull
    @StringValue(pattern = "[A-Za-z-]+")
    private String name;

    @NotNull
    @StringValue(pattern = "[A-Za-z-]+")
    private String city;

    @NotNull
    @IntValue(min = 0, max = 999999999)
    private int id;

    @Setter
    public void setName(String name) {
        this.name = name;
    }

    @Setter
    public void setCity(String city) {
        this.city = city;
    }

    @Setter
    public void setID(int id) {
        this.id = id;
    }

    @Getter
    public String getName() {
        return this.name;
    }

    @Getter
    public String getCity() {
        return this.city;
    }

    @Getter
    public int getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Producer{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", city='" + city + '\'' +
            '}';
    }
}
