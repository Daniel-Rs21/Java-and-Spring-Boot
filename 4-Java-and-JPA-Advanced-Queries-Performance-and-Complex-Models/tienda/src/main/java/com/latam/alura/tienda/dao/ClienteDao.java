package com.latam.alura.tienda.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.modelo.Cliente;

public class ClienteDao {
    private EntityManager em;

    public ClienteDao(EntityManager em) {
        this.em = em;
    }

    public void guardar(Cliente Cliente) {
        this.em.persist(Cliente);
    }

    public Cliente buscarPorId(Long id) {
        return this.em.find(Cliente.class, id);
    }

    public List<Cliente> buscarTodos() {
        String jpql = "SELECT p FROM Cliente p";
        return this.em.createQuery(jpql, Cliente.class).getResultList();
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        String jpql = "SELECT p FROM Cliente p WHERE p.nombre = :nombre";
        return this.em.createQuery(jpql, Cliente.class)
                .setParameter("nombre", nombre).getResultList();
    }
    
}
