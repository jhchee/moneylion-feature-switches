package com.github.jhchee.moneylionfeatureswitches.model;

import javax.persistence.*;

@Table(name = "feature")
@Entity
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    public Feature(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Feature(String name) {
        this.name = name;
    }

    public Feature() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}