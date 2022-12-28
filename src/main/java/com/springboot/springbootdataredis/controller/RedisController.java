package com.springboot.springbootdataredis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.springbootdataredis.dao.ProductDao;
import com.springboot.springbootdataredis.entity.Product;

@RestController
@RequestMapping("/product")
@EnableCaching
public class RedisController {
	
	
	@Autowired
	private ProductDao dao;
	
	@PostMapping
	public Product save(@RequestBody Product product) {		
		return dao.save(product);
	}
	
	@GetMapping
	public List<Object> getAllProducts() {		
		return dao.findAll();
	}
	
	@Cacheable(key="#id",value="Product", unless="#result.price >1000 ")   //not to cache the Product by id whose price is greater than 100
	@GetMapping("/{id}")
	public Object findProductById(@PathVariable int id) {		
		return  dao.findById(id);
		
	}
	@DeleteMapping("/{id}")
	@CacheEvict(key="#id", value="Product") //to delete the record from cache as well the DB
	public String deleteProductById(@PathVariable int id) {		
		return dao.deleteProduct(id);
	}
}
