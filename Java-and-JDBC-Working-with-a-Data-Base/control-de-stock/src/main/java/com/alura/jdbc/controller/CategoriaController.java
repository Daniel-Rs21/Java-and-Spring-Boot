package com.alura.jdbc.controller;

import java.util.List;

import com.alura.jdbc.dao.CategoriaDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;

public class CategoriaController {

    private CategoriaDAO categoriaDAO;
    
    public CategoriaController() {
        this.categoriaDAO = new CategoriaDAO(new ConnectionFactory().recuperaConexion());
    }

	public List<Categoria> listar() {
		// TODO
		return categoriaDAO.listarCategoria();
	}

    public List<Categoria> cargaReporte() {
        // TODO
        return categoriaDAO.cargaReporte();
    }
}
