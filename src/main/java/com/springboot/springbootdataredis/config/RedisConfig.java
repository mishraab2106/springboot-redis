package com.springboot.springbootdataredis.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.springboot.springbootdataredis.entity.Product;

@Configuration
@EnableConfigurationProperties
@EnableRedisRepositories
public class RedisConfig {
	
	@Bean
	public JedisConnectionFactory connectionfactory() {
		
		RedisStandaloneConfiguration configuration=new RedisStandaloneConfiguration();
		
		configuration.setHostName("localhost");
		configuration.setPort(6379);
		return new JedisConnectionFactory(configuration);
	}
	
	@Bean
	public RedisTemplate<String,Product> redistemplate(){
		
		RedisTemplate<String, Product> template=new RedisTemplate<String,Product>();
		template.setConnectionFactory(connectionfactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer()); //this is for the RedisHash
		template.setHashKeySerializer(new JdkSerializationRedisSerializer());
		template.setHashValueSerializer(new JdkSerializationRedisSerializer());
		template.setEnableTransactionSupport(true);
		template.afterPropertiesSet();
		return template;
		
	}

}
