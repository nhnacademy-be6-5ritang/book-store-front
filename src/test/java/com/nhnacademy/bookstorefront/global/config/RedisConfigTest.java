package com.nhnacademy.bookstorefront.global.config;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootTest(classes = RedisConfig.class)
class RedisConfigTest {

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private RedisCacheManager redisCacheManager;

	@BeforeEach
	void setUp() {
		// Optional: You can perform some setup before each test if needed.
	}

	@Test
	void testRedisConnectionFactory() {
		assertThat(redisConnectionFactory).isNotNull();
	}

	@Test
	void testRedisTemplate() {
		assertThat(redisTemplate).isNotNull();
		assertThat(redisTemplate.getKeySerializer()).isInstanceOf(StringRedisSerializer.class);
		assertThat(redisTemplate.getValueSerializer()).isInstanceOf(GenericJackson2JsonRedisSerializer.class);
		assertThat(redisTemplate.getHashKeySerializer()).isInstanceOf(StringRedisSerializer.class);
		assertThat(redisTemplate.getHashValueSerializer()).isInstanceOf(GenericJackson2JsonRedisSerializer.class);
	}

	@Test
	void testRedisCacheManager() {
		assertThat(redisCacheManager).isNotNull();
		assertThat(redisCacheManager.getCacheNames()).isNotNull();
	}
}