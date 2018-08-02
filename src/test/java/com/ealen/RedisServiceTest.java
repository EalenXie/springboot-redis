package com.ealen;

import com.ealen.model.People;
import com.ealen.service.PeopleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisServiceTest {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private PeopleService peopleService;

    @Test
    public void testValue() {
        redisTemplate.opsForValue().set("name", "ealenxie");
        System.out.println(redisTemplate.opsForValue().get("name"));
        redisTemplate.opsForValue().set("age", "23", 10, TimeUnit.SECONDS);
    }

    @Test
    public void testSet() {
        redisTemplate.opsForSet().add("person", "ealen");
        redisTemplate.opsForSet().add("person", "zhangsan", "lisi", "wangwu");
        System.out.println(redisTemplate.opsForSet().pop("person"));//随机pop
        System.out.println(redisTemplate.opsForSet().size("person"));
    }

    @Test
    public void testSavePeople() {
        People people = new People();
        people.setAge(23);
        people.setGender("男");
        people.setName("ealenxie");
        People p = peopleService.savePeople(people);
        Assert.assertNotNull(p);
    }

    @Test
    public void testFindByName() {
        String name = "ealenxie";
        People p = peopleService.findByName(name);
        People people = (People) redisTemplate.opsForValue().get("ealenxie");
        Assert.assertEquals(p, people);
    }

    @Test
    public void testRemove() {
        People people = (People) redisTemplate.opsForValue().get("ealenxie");
        if (people != null) peopleService.removePeople(people);
        people = (People) redisTemplate.opsForValue().get("ealenxie");
        Assert.assertNull(people);
    }
}
