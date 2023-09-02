package com.alura.jdbc.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Categoria;

public class CategoriaDAO {

    private final Connection con;

   public CategoriaDAO(Connection con) {
        this.con = con;
    }

    public List<Categoria> listarCategoria(){

        return new ArrayList<>();
    }

    public List<Categoria> cargaReporte(){
        return new ArrayList<>();
    }

}
