package test;

import static org.junit.Assert.assertNotNull;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/** 
 * 缓存测试
 * @author yilen
 * @date Jun 19, 2018 4:38:31 PM  
 */
@Component
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring/redisConfig.xml")
public class CacheTest {
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ShardedJedisPool shardedJedisPool;
    
    @Autowired
    private ApplicationContext applicationContext;
    
    private  ShardedJedis shardJedis;
    
    @Before
    public void before() {
        Map<String, Object> beansOfType = applicationContext.getBeansOfType(Object.class);
        Set<Entry<String, Object>> entrySet = beansOfType.entrySet();
        for (Entry<String, Object> entry : entrySet) {
            System.out.println(entry.getKey() + ": " + entry.getValue().getClass().getSimpleName());
        }
        assertNotNull(shardedJedisPool);
//        System.out.println(stringRedisTemplate.getValueSerializer());
//        System.out.println(redisTemplate);
        this.shardJedis = shardedJedisPool.getResource();
    }
    

    @Test
    public void setValue() {
       this.shardJedis.set("hello", "hello redis!");
    }
    
    @Test
    public void getValue() {
        System.out.println(this.shardJedis.get("hello"));
    }
}
