package com.springboot.webflux.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.springboot.webflux.app.models.dao.ProductDao;
import com.springboot.webflux.app.models.document.Product;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

	private final static Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ReactiveMongoTemplate monoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		monoTemplate.dropCollection("products").subscribe();
		Flux.just(new Product("Macbook Pro", 1000.90), new Product("Mouse Mx", 50.50),
				new Product("Keyboard qwerty", 60.00), new Product("Remote control", 20.00), new Product("Pad", 10.00),
				new Product("HeadSet", 20.00)).flatMap(product -> {
					product.setCreatedAt(new Date());
					return productDao.save(product);

				}).subscribe(product -> log.info("Insert " + product.getId()));

	}

}
