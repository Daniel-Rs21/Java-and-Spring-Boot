package com.latam.alura.tienda.modelo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "categorias")
public class Categoria {    
    @EmbeddedId
    private CategoriaId categoriaId;

    public Categoria() {
    }

    public Categoria(String nombre) { 
        this.categoriaId = new CategoriaId(nombre, "1234");
    }

    public String getNombre() {
        return categoriaId.getNombre();
    }

    public void setNombre(String nombre) {
        this.categoriaId.setNombre(nombre);
    }
}
