package com.ealen.dao;

import com.ealen.model.People;
import org.springframework.data.repository.CrudRepository;

public interface PeopleRepository extends CrudRepository<People, Integer> {

    People findByName(String name);
}
