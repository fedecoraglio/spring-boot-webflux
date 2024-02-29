package com.springboot.webflux.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.springboot.webflux.app.models.document.Product;

public interface ProductDao extends ReactiveMongoRepository<Product, String> {

}
