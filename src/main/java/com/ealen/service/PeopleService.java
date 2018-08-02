package com.ealen.service;

import com.ealen.dao.PeopleRepository;
import com.ealen.model.People;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PeopleService {


    @Resource
    private PeopleRepository repository;


    @Cacheable(value = "user", key = "#name", unless = "#result eq null")
    public People findByName(String name) {     //查询用户
        return repository.findByName(name);
    }

    @CachePut(value = "user", key = "#result.name", unless = "#result eq null")
    public People savePeople(People people) {   //保存用户
        return repository.save(people);
    }

    @CacheEvict(value = "user", key = "#people.name")
    public void removePeople(People people) {   //删除用户
        repository.delete(people);
    }


}
