package com.springboot.webflux.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.webflux.app.models.document.Product;
import com.springboot.webflux.app.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/api/products")
public class ProductRestController {
	
	
	private final static Logger log = LoggerFactory.getLogger(ProductRestController.class);


	@Autowired
	private ProductService productService;
	
	@GetMapping()
	public Flux<Product> getProducts() {
		final Flux<Product> products = productService.getProducts()
				.doOnNext(product -> log.info(product.getName()));
		return products;
	}
	
	@GetMapping("{id}")
	public Mono<Product> getProduct(@PathVariable() String id) {
		return productService.getProduct(id);
	}

}
