package com.alura.jdbc.controller;

import java.util.List;

import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {

	private ProductoDAO productoDAO;
	
	public ProductoController()  {
		this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
	}

	public int modificar(Producto producto) {
		return productoDAO.modificarProducto(producto);
	}

	public int eliminar(Integer id) {
		return productoDAO.eliminarProducto(id);
	}

	public List<Producto> listar() {		
		return productoDAO.listarProducto();
	}

	public void guardar(Producto producto) {
		productoDAO.guardarProducto(producto);
	}

}
