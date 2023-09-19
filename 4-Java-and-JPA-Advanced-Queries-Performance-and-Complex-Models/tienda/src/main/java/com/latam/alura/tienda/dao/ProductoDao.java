package com.latam.alura.tienda.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
        return em.find(Producto.class, id);
    }

    public List<Producto> buscarTodos() {
        String jpql = "SELECT p FROM Producto p";
        return em.createQuery(jpql, Producto.class).getResultList();
    }

    public List<Producto> buscarPorNombre(String nombre) {
        String jpql = "SELECT p FROM Producto p WHERE p.nombre = :nombre";
        return em.createQuery(jpql, Producto.class)
                .setParameter("nombre", nombre).getResultList();
    }

    public List<Producto> buscarPorCategoria(String nombre) {
        String jpql = "SELECT p FROM Producto p WHERE p.categoria.nombre = :nombre";
        return em.createQuery(jpql, Producto.class)
                .setParameter("nombre", nombre).getResultList();
    }

    public BigDecimal buscarPrecioConNombre(String nombre) {
        return em.createNamedQuery("Producto.buscarPrecioConNombre", BigDecimal.class)
                .setParameter("nombre", nombre).getSingleResult();
    }

    public List<Producto> buscarPorParametro(String nombre, BigDecimal precio, LocalDate fecha) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM Producto p WHERE 1=1");
        if (nombre != null && !nombre.trim().isEmpty()) {
            jpql.append(" AND p.nombre = :nombre");
        }
        if (precio != null && !precio.equals(BigDecimal.ZERO)) {
            jpql.append(" AND p.precio = :precio");
        }
        if (fecha != null) {
            jpql.append(" AND p.fechaDeRegistro = :fecha");
        }

        TypedQuery<Producto> query = em.createQuery(jpql.toString(), Producto.class);
        if (nombre != null && !nombre.trim().isEmpty()) {
            query.setParameter("nombre", nombre);
        }
        if (precio != null && !precio.equals(BigDecimal.ZERO)) {
            query.setParameter("precio", precio);
        }
        if (fecha != null) {
            query.setParameter("fecha", fecha);
        }

        return query.getResultList();
    }

    public List<Producto> buscarPorParametroApiCriteria(String nombre, BigDecimal precio, LocalDate fecha) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Producto> query = builder.createQuery(Producto.class);
        Root<Producto> root = query.from(Producto.class);

        Predicate filtro = builder.and();
        if (nombre != null && !nombre.trim().isEmpty()) {
            filtro = builder.and(filtro, builder.equal(root.get("nombre"), nombre));
        }
        if (precio != null && !precio.equals(BigDecimal.ZERO)) {
            filtro = builder.and(filtro, builder.equal(root.get("precio"), precio));
        }
        if (fecha != null) {
            filtro = builder.and(filtro, builder.equal(root.get("fechaDeRegistro"), fecha));
        }

        query = query.where(filtro);

        return em.createQuery(query).getResultList();
    }
}
