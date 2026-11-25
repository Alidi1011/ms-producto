package com.reto.pacifico.ms_producto.controller;

import com.reto.pacifico.ms_producto.model.Product;
import com.reto.pacifico.ms_producto.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    public Flux<Product> listar() { return service.listarActivos(); }

    @GetMapping("/{id}")
    public Mono<Product> obtener(@PathVariable Long id) { return service.buscarPorId(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> crear(@RequestBody Product p) { return service.crear(p); }

    @PutMapping("/{id}")
    public Mono<Product> actualizar(@PathVariable Long id, @RequestBody Product p) {
        return service.actualizar(id, p);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminar(@PathVariable Long id) { return service.eliminar(id); }
}
