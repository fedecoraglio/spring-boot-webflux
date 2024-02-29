package com.springboot.webflux.app.controller;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

import com.springboot.webflux.app.models.document.Product;
import com.springboot.webflux.app.service.ProductService;

import reactor.core.publisher.Flux;

@Controller
public class ProductController {
	
	private final static Logger log = LoggerFactory.getLogger(ProductController.class);


	@Autowired
	private ProductService productService;
	
	@GetMapping({"/list", "/"})
	public String getProducts(final Model model) {
		
		final Flux<Product> products = productService.getProducts();
		
		products.subscribe(product -> log.info(product.getName()));
		
		model.addAttribute("products", products);
		model.addAttribute("title", "Product List");
		
		return "product-list";
		
	}
	
	@GetMapping("list-full")
	public String getProductsFull(final Model model) {
		
		final Flux<Product> products = productService.getProducts().repeat(5000);
		
		model.addAttribute("products", products);
		model.addAttribute("title", "Product List");
		
		return "product-list";
		
	}
	
	@GetMapping("list-chunked")
	public String getProductsChunked(final Model model) {
		
		final Flux<Product> products = productService.getProducts().repeat(5000);
		
		model.addAttribute("products", products);
		model.addAttribute("title", "Product List");
		
		return "product-chunked";
		
	}
	
	@GetMapping("/list-datadriver")
	public String getProductsDataDriver(final Model model) {
		
		final Flux<Product> products = productService.getProducts().delayElements(Duration.ofSeconds(1));
		
		products.subscribe(product -> log.info(product.getName()));
		
		model.addAttribute("products", new ReactiveDataDriverContextVariable(products, 2));
		model.addAttribute("title", "Product List");
		
		return "product-list";
		
	}
}
