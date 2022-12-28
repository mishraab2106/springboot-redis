package com.springboot.springbootdataredis.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.springboot.springbootdataredis.entity.Product;

@Repository
public class ProductDao {
	
	public static final String HASH_KEY="Product";
	
	@Autowired
	private RedisTemplate<String,Product> template;
	
	public Product save (Product product) {
		//opsForHash gets the hash of it
		template.opsForHash().put(HASH_KEY, product.getId(), product);
		return product;		
	}
	
	
	public List<Object> findAll() {
		//opsForHash gets the hash of it
		return template.opsForHash().values(HASH_KEY);//because the domain object we are persisting as a hashkey in redis
		
	}
	
	public Object findById (int id) {
		//opsForHash gets the hash of it
		System.out.println("called from DB"); ///will be called from the DB once and will then be updated on the caching
		return template.opsForHash().get(HASH_KEY, id);
	}
	
	
	public String deleteProduct (int id) {
		//opsForHash gets the hash of it
		 template.opsForHash().delete(HASH_KEY, id);
		return "Deleted";
	}

}
