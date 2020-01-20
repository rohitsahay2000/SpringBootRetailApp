package com.springboot.myretail.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.springboot.myretail.model.Product;
import com.springboot.myretail.repo.ProductRepo;

@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Override
	public Product add(Product product) {
		
		product.getPrice().setProduct(product);
		return productRepo.save(product);

	}

	@Override
	public List<Product> getProductList() {
		return productRepo.findAll();
	}

	@Override
	public Product getProduct(Integer productId) {
		Product product = productRepo.findById(productId).orElse(null);
		if(product==null)
			throw new EmptyResultDataAccessException("", productId);
		return product;
	}

	@Override
	public Product updateProduct(Product product, Integer productId) {
		Product existingProduct = productRepo.findById(productId).orElse(null);
		if(existingProduct==null)
			throw new EmptyResultDataAccessException("", productId);
		if (product.getName() != null)
			existingProduct.setName(product.getName());
		if (product.getPrice()!=null && product.getPrice().getCurrency() != null)
			existingProduct.getPrice().setCurrency(product.getPrice().getCurrency());
		if (product.getPrice()!=null && product.getPrice().getValue() != null)
			existingProduct.getPrice().setValue(product.getPrice().getValue());	
		return productRepo.save(existingProduct);
	}

}
