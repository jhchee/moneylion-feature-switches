package com.github.jhchee.moneylionfeatureswitches.repository;

import com.github.jhchee.moneylionfeatureswitches.model.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepo extends CrudRepository<User, Integer> {

    User findUserByEmail(String email);
}