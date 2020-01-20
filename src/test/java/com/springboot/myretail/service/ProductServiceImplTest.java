package com.springboot.myretail.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import com.springboot.myretail.model.Product;
import com.springboot.myretail.model.ProductPrice;
import com.springboot.myretail.repo.ProductRepo;



public class ProductServiceImplTest {

	@Mock
	private ProductRepo productrepo;

	@InjectMocks
	private ProductServiceImpl prodService;

	@Before
	public void setup() throws Exception {
		prodService = new ProductServiceImpl();
		MockitoAnnotations.initMocks(this);

	}

	@Test(expected = NullPointerException.class)
	public void testAddNullProduct() throws Exception {
		prodService.add(null);
	}

	@Test(expected = NullPointerException.class)
	public void testAddProductAllParametersNull() throws Exception {
		Product product = new Product();
		prodService.add(product);

		Product product1 = new Product();
		product1.setName("test");
		prodService.add(product);
	}



	@Test
	public void testAddProductAllParametersValid() throws Exception {
		Product product = new Product();
		product.setName("test");
		ProductPrice productPrice = new ProductPrice();
		productPrice.setCurrency("USD");
		productPrice.setValue(300.0);
		product.setPrice(productPrice);
		prodService.add(product);
		Mockito.verify(productrepo, Mockito.times(1)).save(product);
	}

	@Test
	public void testgetProductList() throws Exception {
		prodService.getProductList();
		Mockito.verify(productrepo, Mockito.times(1)).findAll();
	}

	@Test(expected = NullPointerException.class)
	public void testgetProductWithProductIdNull() throws Exception {
		prodService.getProduct(null);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void testgetProduct() throws Exception {
		prodService.getProduct(123);
		Mockito.verify(productrepo, Mockito.times(1)).findById(123).orElse(null);
	}

	@Test(expected = NullPointerException.class)
	public void testUpdateProductWithProductIdNull() throws Exception {
		prodService.updateProduct(new Product(), null);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void testUpdateProductWithProductNull() throws Exception {
		prodService.updateProduct(null, 123);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void testUpdateProductWithPriceNull() throws Exception {
		prodService.updateProduct(new Product(), 123);
	}

	@Test
	public void testUpdateProduct() throws Exception {
		Product product = new Product();
		product.setName("test");
		ProductPrice productPrice = new ProductPrice();
		productPrice.setCurrency("USD");
		productPrice.setValue(300.0);
		product.setPrice(productPrice);

		Product updatedProduct = new Product();
		updatedProduct.setName("test1");
		ProductPrice updatedProductPrice = new ProductPrice();
		updatedProductPrice.setCurrency("USD");
		updatedProductPrice.setValue(310.0);
		updatedProduct.setPrice(updatedProductPrice);

		Mockito.when(productrepo.findById(Mockito.anyInt())).thenReturn(Optional.of(product));
		prodService.updateProduct(updatedProduct, 123);
		Assert.assertEquals(product.getName(), updatedProduct.getName());
		Assert.assertEquals(product.getPrice().getCurrency(), updatedProduct.getPrice().getCurrency());
		Assert.assertEquals(product.getPrice().getValue(), updatedProduct.getPrice().getValue());
		Mockito.verify(productrepo, Mockito.times(1)).save(product);

	}

	@Test
	public void testUpdateProductWithNullDetails() throws Exception {
		Product product = new Product();
		product.setName("test");
		ProductPrice productPrice = new ProductPrice();
		productPrice.setCurrency("USD");
		productPrice.setValue(300.0);
		product.setPrice(productPrice);

		Product updatedProduct = new Product();
		ProductPrice updatedProductPrice = new ProductPrice();
		updatedProduct.setPrice(updatedProductPrice);

		Mockito.when(productrepo.findById(Mockito.anyInt())).thenReturn(Optional.of(product));
		prodService.updateProduct(updatedProduct, 123);
		Assert.assertEquals(product.getName(), "test");
		Assert.assertEquals(product.getPrice().getCurrency(), "USD");
		Mockito.verify(productrepo, Mockito.times(1)).save(product);

	}

}
