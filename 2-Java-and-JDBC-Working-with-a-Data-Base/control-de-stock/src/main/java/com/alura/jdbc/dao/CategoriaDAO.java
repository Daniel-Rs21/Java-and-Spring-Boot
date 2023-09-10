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
        List<Categoria> resultado = new ArrayList<>();

        try (con) {
            PreparedStatement statement = con.prepareStatement("SELECT C.id, C.nombre, P.id, P.nombre, P.cantidad FROM CATEGORIA C INNER JOIN PRODUCTO P ON C.id = P.categoria_id");

            try (statement) {
                final ResultSet resultSet = statement.executeQuery();

                try (resultSet) {
                    while (resultSet.next()) {

                        Integer categoriaId = resultSet.getInt("id");
                        String categoriaNombre = resultSet.getString("nombre");

                        var categoria = resultado.stream().
                        filter(cat -> cat.getId().equals(categoriaId))
                        .findAny().orElseGet(() -> {
                            var cat = new Categoria(categoriaId, categoriaNombre);
                            resultado.add(cat);
                            return cat;
                        });

                        Producto producto = new Producto(
                                resultSet.getInt("P.id"),
                                resultSet.getString("P.nombre"),
                                resultSet.getInt("P.cantidad"));

                        categoria.agregar(producto);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }
}
