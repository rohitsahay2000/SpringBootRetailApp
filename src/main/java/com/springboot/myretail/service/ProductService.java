package com.springboot.myretail.service;

import java.util.List;

import com.springboot.myretail.model.Product;

public interface ProductService {
	
	Product add(Product product);
	
	List<Product> getProductList();
	
	Product getProduct(Integer productId); 
	
	Product updateProduct(Product product, Integer productId);
}
