package com.github.jhchee.moneylionfeatureswitches.repository;

import com.github.jhchee.moneylionfeatureswitches.model.Feature;
import org.springframework.data.repository.CrudRepository;

public interface IFeatureRepo extends CrudRepository<Feature, Integer> {

    Feature findFeatureByName(String name);
}