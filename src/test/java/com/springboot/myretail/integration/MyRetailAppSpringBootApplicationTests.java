package com.springboot.myretail.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.myretail.model.Product;
import com.springboot.myretail.response.ProductResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyRetailAppSpringBootApplicationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();

	@Test
	void contextLoads() {
	}

	@Test
	public void testCreateProduct() throws Exception {
		
		String body =  "{\"name\":\"productABC\",\"price\" : {\"value\":\"217.49\",\"currency\":\"INR\"} }";
		headers.add("Content-Type", "application/json");
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/product"), HttpMethod.POST, entity,
				String.class);
		HttpStatus statusCode = response.getStatusCode();
		assertEquals(statusCode,HttpStatus.CREATED);
		
		ResponseEntity<String> response1 = restTemplate.exchange(createURLWithPort("/product"), HttpMethod.POST, entity,
				String.class);
		HttpStatus statusCode1 = response1.getStatusCode();
		assertEquals(statusCode1,HttpStatus.CONFLICT);
		
		String body1 =  "{\"name\":\"productABC\",\"price\" : {\"currency\":\"INR\"} }";
		HttpEntity<String> entity1 = new HttpEntity<String>(body1, headers);
		ResponseEntity<String> response2 = restTemplate.exchange(createURLWithPort("/product"), HttpMethod.POST, entity1,
				String.class);
		HttpStatus statusCode2 = response2.getStatusCode();
		assertEquals(statusCode2,HttpStatus.BAD_REQUEST);
		
		String body2 = "{\"price\" : {\"value\":\"217.49\",\"currency\":\"INR\"} }";
		HttpEntity<String> entity2 = new HttpEntity<String>(body2, headers);
		ResponseEntity<String> response3 = restTemplate.exchange(createURLWithPort("/product"), HttpMethod.POST, entity2,
				String.class);
		HttpStatus statusCode3 = response3.getStatusCode();
		assertEquals(statusCode3,HttpStatus.BAD_REQUEST);
		
		String body3 = "";
		HttpEntity<String> entity3 = new HttpEntity<String>(body3, headers);
		ResponseEntity<String> response4 = restTemplate.exchange(createURLWithPort("/product"), HttpMethod.POST, entity3,
				String.class);
		HttpStatus statusCode4 = response4.getStatusCode();
		assertEquals(statusCode4,HttpStatus.BAD_REQUEST);
		
	}
	
	
	@Test
	public void testGetProductList() throws Exception {
		
		String body =  "{\"name\":\"productABC\",\"price\" : {\"value\":\"217.49\",\"currency\":\"INR\"} }";
		headers.add("Content-Type", "application/json");
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		
		restTemplate.exchange(createURLWithPort("/product"), HttpMethod.POST, entity,
				String.class);
		
		String body1 =  "{\"name\":\"productDEF\",\"price\" : {\"value\":\"317.49\",\"currency\":\"USD\"} }";
		headers.add("Content-Type", "application/json");
		
		HttpEntity<String> entity1 = new HttpEntity<String>(body1, headers);
		
		restTemplate.exchange(createURLWithPort("/product"), HttpMethod.POST, entity1,
				String.class);
		
		HttpEntity<List<Product>> productListEntity = new HttpEntity<List<Product>>(null, headers);
		
		ResponseEntity<Product[]> productList=restTemplate.exchange(createURLWithPort("/product"), HttpMethod.GET, productListEntity,
				Product[].class);
		
		assertTrue(productList.getBody().length>0);
			
	}
	
	@Test
	public void testGetProduct() throws Exception {
		
		String body =  "{\"name\":\"productXYZ\",\"price\" : {\"value\":\"217.49\",\"currency\":\"DIN\"} }";
		headers.add("Content-Type", "application/json");
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		
		ResponseEntity<Product> product = restTemplate.exchange(createURLWithPort("/product"), HttpMethod.POST, entity,
				Product.class);
		
		Integer createdProductId = product.getBody().getId();
		
		HttpEntity<Product> entity1 = new HttpEntity<Product>(null, headers);
		
		ResponseEntity<Product> returnedProduct=restTemplate.exchange(createURLWithPort("/product/"+createdProductId), HttpMethod.GET, entity1,
				Product.class);
		assertEquals("productXYZ",returnedProduct.getBody().getName());
			
	}
	
	@Test
	public void testUpdateProduct() throws Exception {
		
		String body =  "{\"name\":\"productMNP\",\"price\" : {\"value\":\"417.49\",\"currency\":\"USD\"} }";
		headers.add("Content-Type", "application/json");
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		
		ResponseEntity<Product> product = restTemplate.exchange(createURLWithPort("/product"), HttpMethod.POST, entity,
				Product.class);
		
		Integer createdProductId = product.getBody().getId();
		
		String body1 =  "{\"name\":\"productMNP_Updated\",\"price\" : {\"value\":\"417.49\",\"currency\":\"USD\"} }";
		headers.add("Content-Type", "application/json");
		
		HttpEntity<String> entity1 = new HttpEntity<String>(body1, headers);

		ResponseEntity<ProductResponse> updatedProduct=restTemplate.exchange(createURLWithPort("/product/"+createdProductId), HttpMethod.PUT, entity1,
				ProductResponse.class);
		
		
		HttpEntity<Product> entity2 = new HttpEntity<Product>(null, headers);
		
		ResponseEntity<Product> returnedProduct=restTemplate.exchange(createURLWithPort("/product/"+createdProductId), HttpMethod.GET, entity2,
				Product.class);
	
		assertEquals("productMNP_Updated",returnedProduct.getBody().getName());
	}
	
	
	
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
