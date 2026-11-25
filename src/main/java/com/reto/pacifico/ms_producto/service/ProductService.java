package com.reto.pacifico.ms_producto.service;

import com.reto.pacifico.ms_producto.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<Product> listarActivos();

    Mono<Product> buscarPorId(Long id);

    Mono<Product> crear(Product product);

    Mono<Product> actualizar(Long id, Product product);

    Mono<Void> eliminar(Long id);
}
