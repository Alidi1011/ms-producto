package com.reto.pacifico.ms_producto.service;

import com.reto.pacifico.ms_producto.model.Product;
import com.reto.pacifico.ms_producto.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    public Flux<Product> listarActivos() {
        return repository.findByActivoTrue();
    }

    public Mono<Product> buscarPorId(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado: " + id)));
    }

    public Mono<Product> crear(Product p) {
        return Mono.just(p)
                .map(prod -> new Product(null,
                        Optional.ofNullable(prod.nombre()).orElseThrow(() -> new IllegalArgumentException("Nombre requerido")),
                        prod.descripcion(),
                        Optional.ofNullable(prod.precio()).filter(v -> v > 0).orElseThrow(() -> new IllegalArgumentException("Precio debe ser > 0")),
                        Optional.ofNullable(prod.stock()).orElse(0),
                        true))
                .flatMap(repository::save)
                .onErrorResume(DataIntegrityViolationException.class,
                        ex -> Mono.error(new IllegalArgumentException("No se pudo guardar el producto", ex)));
    }

    public Mono<Product> actualizar(Long id, Product p) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado: " + id)))
                .map(existente -> new Product(id, p.nombre(), p.descripcion(), p.precio(), p.stock(), existente.activo()))
                .flatMap(repository::save);
    }

    public Mono<Void> eliminar(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado: " + id)))
                .flatMap(prod -> repository.deleteById(id));
    }
}
