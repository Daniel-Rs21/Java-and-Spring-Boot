package com.latam.alura.tienda.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.modelo.Pedido;
import com.latam.alura.tienda.vo.ResumenDeVentas;

public class PedidoDao {
    private EntityManager em;

    public PedidoDao(EntityManager em) {
        this.em = em;
    }

    public void guardar(Pedido Pedido) {
        this.em.persist(Pedido);
    }

    public Pedido buscarPorId(Long id) {
        return this.em.find(Pedido.class, id);
    }

    public List<Pedido> buscarTodos() {
        String jpql = "SELECT p FROM Pedido p";
        return this.em.createQuery(jpql, Pedido.class).getResultList();
    }

    public BigDecimal valorTotalVendido() {
        String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
        return this.em.createQuery(jpql, BigDecimal.class).getSingleResult();
    }

    public List<Object[]> resumenDeVentas() {
        String jpql = "SELECT producto.nombre, "
                + "SUM(item.cantidad), "
                + "MAX(pedido.fecha) "
                + "FROM Pedido pedido "
                + "JOIN pedido.itemsPedidos item "
                + "JOIN item.producto producto "
                + "GROUP BY producto.nombre "
                + "ORDER BY item.cantidad DESC";
        return this.em.createQuery(jpql, Object[].class).getResultList();
    }

    public List<ResumenDeVentas> resumenDeVentasVo() {
        String jpql = "SELECT new com.latam.alura.tienda.vo.ResumenDeVentas(producto.nombre, "
                + "SUM(item.cantidad), "
                + "MAX(pedido.fecha)) "
                + "FROM Pedido pedido "
                + "JOIN pedido.itemsPedidos item "
                + "JOIN item.producto producto "
                + "GROUP BY producto.nombre "
                + "ORDER BY item.cantidad DESC";
        return this.em.createQuery(jpql, ResumenDeVentas.class).getResultList();
    }

    public Pedido buscarPedidoConCliente(Long id) {
        String jpql = "SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id";
        return this.em.createQuery(jpql, Pedido.class)
                .setParameter("id", id)
                .getSingleResult();
    } 

    
}
