package com.empresa.catalogo.controller;

import com.empresa.catalogo.dto.ProductoRequestDTO;
import com.empresa.catalogo.dto.ProductoResponseDTO;
import com.empresa.catalogo.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones CRUD del catálogo")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Crear un nuevo producto")
    @ApiResponse(responseCode = "201", description = "Producto creado")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        ProductoResponseDTO creado = productoService.crear(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        return ResponseEntity.ok(productoService.listarActivos());
    }
    @Operation(summary = "Obtener producto por ID")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
    }
}
