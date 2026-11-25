package com.reto.pacifico.ms_producto.service;

import com.reto.pacifico.ms_producto.model.Product;
import com.reto.pacifico.ms_producto.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock ProductRepository repository;
    @InjectMocks ProductService service;

    @Test void listar() {
        when(repository.findByActivoTrue()).thenReturn(Flux.just(new Product(1L,"SOAT","x",120.0,10,true)));
        StepVerifier.create(service.listarActivos()).expectNextCount(1).verifyComplete();
    }
    @Test void crearOk() {
        Product p = new Product(null,"SOAT","x",120.0,10,true);
        when(repository.save(any())).thenReturn(Mono.just(new Product(1L,"SOAT","x",120.0,10,true)));
        StepVerifier.create(service.crear(p)).expectNextCount(1).verifyComplete();
    }
    @Test void crearPrecioInvalido() {
        StepVerifier.create(service.crear(new Product(null,"SOAT","x",-1.0,10,true)))
                .expectError(IllegalArgumentException.class).verify();
    }
    @Test void crearErrorDePersistencia() {
        Product p = new Product(null,"SOAT","x",120.0,10,true);
        when(repository.save(any())).thenReturn(Mono.error(new DataIntegrityViolationException("conflicto")));

        StepVerifier.create(service.crear(p))
                .expectErrorMatches(error -> error instanceof IllegalArgumentException
                        && error.getMessage().equals("No se pudo guardar el producto")
                        && error.getCause() instanceof DataIntegrityViolationException)
                .verify();
    }
    @Test void buscarNoExiste() {
        when(repository.findById(99L)).thenReturn(Mono.empty());
        StepVerifier.create(service.buscarPorId(99L)).expectError(RuntimeException.class).verify();
    }
    @Test void actualizarOk() {
        Product existente = new Product(1L,"SOAT","x",120.0,10,true);
        when(repository.findById(1L)).thenReturn(Mono.just(existente));
        when(repository.save(any())).thenReturn(Mono.just(existente));
        StepVerifier.create(service.actualizar(1L, existente)).expectNextCount(1).verifyComplete();
    }
    @Test void eliminarOk() {
        when(repository.findById(1L)).thenReturn(Mono.just(new Product(1L,"SOAT","x",120.0,10,true)));
        when(repository.deleteById(1L)).thenReturn(Mono.empty());
        StepVerifier.create(service.eliminar(1L)).verifyComplete();
    }
}