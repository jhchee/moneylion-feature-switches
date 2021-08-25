package com.github.jhchee.moneylionfeatureswitches.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "feature")
@Entity
public class Feature {

    @Id
    Integer id;

    String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}