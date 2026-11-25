package com.reto.pacifico.ms_producto.repository;

import com.reto.pacifico.ms_producto.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    Flux<Product> findByActivoTrue();
}