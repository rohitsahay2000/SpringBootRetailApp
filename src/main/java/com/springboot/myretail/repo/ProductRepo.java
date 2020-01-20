package com.springboot.myretail.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.myretail.model.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>{
	

}
