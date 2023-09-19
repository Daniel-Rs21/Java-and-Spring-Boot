package com.latam.alura.tienda.modelo;

import javax.persistence.Entity;

@Entity
public class Libros extends Producto {
    private String autor;
    private Integer numeroDePaginas;

    public Libros() {}

    public Libros(String autor, Integer numeroDePaginas) {
        this.autor = autor;
        this.numeroDePaginas = numeroDePaginas;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getNumeroDePaginas() {
        return numeroDePaginas;
    }

    public void setNumeroDePaginas(Integer numeroDePaginas) {
        this.numeroDePaginas = numeroDePaginas;
    }
}
