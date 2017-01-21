package com.noveogroup.java.pojo;

import java.io.Serializable;
import com.noveogroup.java.annotation.*;

/**
 *
 * @author Alexander
 */
public class Client implements Serializable {

    @NotNull
    @StringValue(pattern = "[A-Za-z-]+")
    public String name;

    @NotNull
    @StringValue(pattern = "[A-Za-z-]+")
    private String city;

    @NotNull
    @IntValue(min = 0, max = 999999999)
    private int id;

    @Nullable
    @IntValue(min = 0, max = 999999999)
    private int balance;

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

    @Setter
    public void setBalance(int balance) {
        this.balance = balance;
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

    @Getter
    public int getBalance() {
        return this.balance;
    }

    // CR3 It very convenient to override toString() method to log this object like
    // the following:
    // Ok, i will use it
    @Override
    public String toString() {
        return "Client{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", city='" + city + '\'' +
            ", balance=" + balance +
            '}';
    }
}
