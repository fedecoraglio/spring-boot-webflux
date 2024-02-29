package com.springboot.webflux.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.webflux.app.models.dao.ProductDao;
import com.springboot.webflux.app.models.document.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;
	
	public Flux<Product> getProducts() {
		return productDao.findAll().map(product -> {
			product.setName(product.getName().toUpperCase());
			return product;
		});
	}
	
	public Mono<Product> getProduct(final String id) {
		return productDao.findById(id);
	}
}
