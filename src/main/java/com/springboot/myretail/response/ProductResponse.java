package com.springboot.myretail.response;

public class ProductResponse {
	private String status;
	private String message;

	private ProductResponse(ProductResponseBuilder productResponseBuilder) {
		this.status = productResponseBuilder.status;
		this.message = productResponseBuilder.message;
	}

	public String getStatus() {
		return status;
	}


	public String getMessage() {
		return message;
	}

	public static class ProductResponseBuilder {

		private String status;
		private String message;

		public ProductResponseBuilder(String status) {
			this.status = status;
		}

		public ProductResponseBuilder message(String message) {
			this.message = message;
			return this;
		}

		public ProductResponse build() {
			ProductResponse productResponse = new ProductResponse(this);
			return productResponse;
		}
	}

}
