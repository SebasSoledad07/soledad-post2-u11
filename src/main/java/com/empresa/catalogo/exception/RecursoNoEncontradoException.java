package com.empresa.catalogo.exception;

public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String producto, Long id) {
        super(producto + " con id " + id + " no encontrado.");
    }


}

