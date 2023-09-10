package com.latam.alura.tienda.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.modelo.Producto;

public class ProductoDao {
    private EntityManager em;

    public ProductoDao(EntityManager em) {
        this.em = em;
    }

    public void guardar(Producto producto) {
        this.em.persist(producto);
    }

    public Producto buscarPorId(Long id) {
        return this.em.find(Producto.class, id);
    }

    public List<Producto> buscarTodos() {
        String jpql = "SELECT p FROM Producto p";
        return this.em.createQuery(jpql, Producto.class).getResultList();
    }

    public List<Producto> buscarPorNombre(String nombre) {
        String jpql = "SELECT p FROM Producto p WHERE p.nombre = :nombre";
        return this.em.createQuery(jpql, Producto.class)
                .setParameter("nombre", nombre).getResultList();
    }

    public List<Producto> buscarPorCategoria(String nombre) {
        String jpql = "SELECT p FROM Producto p WHERE p.categoria.nombre = :nombre";
        return this.em.createQuery(jpql, Producto.class)
                .setParameter("nombre", nombre).getResultList();
    }

    public BigDecimal buscarPrecioConNombre(String nombre) {
        String jpql = "SELECT p.precio FROM Producto p WHERE p.nombre = :nombre";
        return this.em.createQuery(jpql, BigDecimal.class)
                .setParameter("nombre", nombre).getSingleResult();
    }
}
