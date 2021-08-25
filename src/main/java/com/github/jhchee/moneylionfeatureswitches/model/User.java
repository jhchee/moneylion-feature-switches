package com.github.jhchee.moneylionfeatureswitches.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true)
    String email;

    // a table that stores unique pairs of user and feature
    // the existence of user_feature pair means the feature switch is on
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_feature",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "feature_id")
            }
    )
    private Set<Feature> features = new HashSet<>();

    public User(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }
}
