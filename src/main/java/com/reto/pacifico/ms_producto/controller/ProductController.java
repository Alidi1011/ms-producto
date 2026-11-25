package com.reto.pacifico.ms_producto.controller;

import com.reto.pacifico.ms_producto.dto.ProductRequestDto;
import com.reto.pacifico.ms_producto.dto.ProductResponseDto;
import com.reto.pacifico.ms_producto.mapper.ProductMapper;
import com.reto.pacifico.ms_producto.model.Product;
import com.reto.pacifico.ms_producto.service.ProductService;
import jakarta.validation.Valid;
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
    private final ProductMapper mapper;

    @GetMapping
    public Flux<ProductResponseDto> listar() {
        return service.listarActivos().map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ProductResponseDto> obtener(@PathVariable Long id) {
        return service.buscarPorId(id).map(mapper::toResponse);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponseDto> crear(@Valid @RequestBody ProductRequestDto request) {
        return service.crear(mapper.toModel(request)).map(mapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<ProductResponseDto> actualizar(@PathVariable Long id,
                                               @Valid @RequestBody ProductRequestDto request) {
        return service.actualizar(id, mapper.toModel(request)).map(mapper::toResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminar(@PathVariable Long id) { return service.eliminar(id); }
}
