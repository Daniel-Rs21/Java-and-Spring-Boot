package com.latam.alura.tienda.prueba;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.dao.CategoriaDao;
import com.latam.alura.tienda.dao.ClienteDao;
import com.latam.alura.tienda.dao.PedidoDao;
import com.latam.alura.tienda.dao.ProductoDao;
import com.latam.alura.tienda.modelo.Categoria;
import com.latam.alura.tienda.modelo.Cliente;
import com.latam.alura.tienda.modelo.ItemsPedido;
import com.latam.alura.tienda.modelo.Pedido;
import com.latam.alura.tienda.modelo.Producto;
import com.latam.alura.tienda.utils.JPAUtils;
import com.latam.alura.tienda.vo.ResumenDeVentas;

public class RegistroDePedido {
    public static void main(String[] args) {
        Categoria celulares = new Categoria("CELULARES");
        Producto celular = new Producto("Xiaomi Redmi", "Buena condición", new BigDecimal("800"), celulares);

        EntityManager em = JPAUtils.getEntityManager();

        CategoriaDao categoriaDao = new CategoriaDao(em);
        ProductoDao productoDao = new ProductoDao(em);

        em.getTransaction().begin();

        categoriaDao.guardar(celulares);
        productoDao.guardar(celular);

        em.getTransaction().commit();

        Producto producto = productoDao.buscarPorId(1l);

        Cliente cliente = new Cliente("Juan", "12345678");
        Pedido pedido = new Pedido(cliente);

        pedido.agregarItem(new ItemsPedido(5, producto, pedido));

        PedidoDao pedidoDao = new PedidoDao(em);
        ClienteDao clienteDao = new ClienteDao(em);

        em.getTransaction().begin();

        clienteDao.guardar(cliente);
        pedidoDao.guardar(pedido);

        em.getTransaction().commit();

        List<Object[]> resumen = pedidoDao.resumenDeVentas();

        for (Object[] obj : resumen) {
            System.out.println(obj[0] + " | " + obj[1] + " | " + obj[2]);
        }

        List<ResumenDeVentas> resumen2 = pedidoDao.resumenDeVentasVo();

        resumen2.forEach(System.out::println);

        Pedido id = pedidoDao.buscarPorId(1l);

        System.out.println(id.getCliente().getNombre());
    }
}
