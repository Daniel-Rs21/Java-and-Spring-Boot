package com.latam.alura.tienda.prueba;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.dao.CategoriaDao;
import com.latam.alura.tienda.dao.ProductoDao;
import com.latam.alura.tienda.modelo.Categoria;
import com.latam.alura.tienda.modelo.CategoriaId;
import com.latam.alura.tienda.modelo.Producto;
import com.latam.alura.tienda.utils.JPAUtils;

public class RegistroDeProducto {
    public static void main(String[] args) {
        Categoria celulares =  new Categoria("CELULARES");
        Producto celular = new Producto("Xiaomi Redmi", "Buena condición", new BigDecimal("800"), celulares);

        EntityManager em = JPAUtils.getEntityManager();
    
        CategoriaDao categoriaDao = new CategoriaDao(em);
        ProductoDao productoDao = new ProductoDao(em);

        em.getTransaction().begin();
        
        categoriaDao.guardar(celulares);
        productoDao.guardar(celular);

        em.getTransaction().commit();

        BigDecimal precio = productoDao.buscarPrecioConNombre("Xiaomi Redmi");
        System.out.println(precio);

        Categoria categ = em.find(Categoria.class, new CategoriaId("CELULARES", "1234"));
        System.out.println(categ.getNombre());
    }
}
