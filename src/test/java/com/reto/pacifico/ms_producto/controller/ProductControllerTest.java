package com.reto.pacifico.ms_producto.controller;

import com.reto.pacifico.ms_producto.dto.ProductRequestDto;
import com.reto.pacifico.ms_producto.mapper.ProductMapper;
import com.reto.pacifico.ms_producto.model.Product;
import com.reto.pacifico.ms_producto.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

@WebFluxTest(ProductController.class)
@Import(ProductMapper.class)
class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService service;

    @Test
    void listarProductos() {
        when(service.listarActivos()).thenReturn(Flux.just(producto()));

        webTestClient.get()
                .uri("/api/productos")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].nombre").isEqualTo("SOAT");

        verify(service).listarActivos();
    }

    @Test
    void obtenerProductoPorId() {
        when(service.buscarPorId(1L)).thenReturn(Mono.just(producto()));

        webTestClient.get()
                .uri("/api/productos/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.nombre").isEqualTo("SOAT");

        verify(service).buscarPorId(1L);
    }

    @Test
    void crearProducto() {
        when(service.crear(productoSinId())).thenReturn(Mono.just(producto()));

        webTestClient.post()
                .uri("/api/productos")
                .bodyValue(request())
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.nombre").isEqualTo("SOAT");

        verify(service).crear(productoSinId());
    }

    @Test
    void crearProductoConErrorDevuelveBadRequest() {
        when(service.crear(any(Product.class)))
                .thenReturn(Mono.error(new IllegalArgumentException("No se pudo guardar el producto")));

        webTestClient.post()
                .uri("/api/productos")
                .bodyValue(request())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error").isEqualTo("No se pudo guardar el producto");
    }

    @Test
    void actualizarProducto() {
        when(service.actualizar(1L, productoSinId())).thenReturn(Mono.just(producto()));

        webTestClient.put()
                .uri("/api/productos/{id}", 1)
                .bodyValue(request())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.nombre").isEqualTo("SOAT");

        verify(service).actualizar(1L, productoSinId());
    }

    @Test
    void actualizarProductoConPrecioInvalidoDevuelveBadRequest() {
        ProductRequestDto productoInvalido = new ProductRequestDto("SOAT", "Cobertura", 0.0, 10, true);

        webTestClient.put()
                .uri("/api/productos/{id}", 1)
                .bodyValue(productoInvalido)
                .exchange()
                .expectStatus().isBadRequest();

        verifyNoInteractions(service);
    }

    @Test
    void crearProductoResumeTodasLasValidaciones() {
        ProductRequestDto productoInvalido = new ProductRequestDto("", null, null, -1, true);

        webTestClient.post()
                .uri("/api/productos")
                .bodyValue(productoInvalido)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error").value(allOf(
                        containsString("nombre:"),
                        containsString("precio:"),
                        containsString("stock:")));

        verifyNoInteractions(service);
    }

    @Test
    void eliminarProducto() {
        when(service.eliminar(1L)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/productos/{id}", 1)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();

        verify(service).eliminar(1L);
    }

    private Product producto() {
        return new Product(1L, "SOAT", "Cobertura", 120.0, 10, true);
    }

    private Product productoSinId() {
        return new Product(null, "SOAT", "Cobertura", 120.0, 10, true);
    }

    private ProductRequestDto request() {
        return new ProductRequestDto("SOAT", "Cobertura", 120.0, 10, true);
    }
}
