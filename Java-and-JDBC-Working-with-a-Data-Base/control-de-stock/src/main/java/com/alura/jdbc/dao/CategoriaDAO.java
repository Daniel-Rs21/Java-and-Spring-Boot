package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

public class CategoriaDAO {

    private final Connection con;

    public CategoriaDAO(Connection con) {
        this.con = con;
    }

    public List<Categoria> listarCategoria() {
        List<Categoria> resultado = new ArrayList<>();

        try (con) {
            PreparedStatement statement = con.prepareStatement("SELECT id, nombre FROM CATEGORIA");

            try (statement) {
                final ResultSet resultSet = statement.executeQuery();

                try (resultSet) {
                    while (resultSet.next()) {
                        Categoria categoria = new Categoria(
                                resultSet.getInt("id"),
                                resultSet.getString("nombre"));
                        resultado.add(categoria);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

    public List<Categoria> listarConProductos() {
        return null;
    }
}
