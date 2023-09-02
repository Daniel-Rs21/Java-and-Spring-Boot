package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {
    final private Connection con;

    public ProductoDAO(Connection con) {
        this.con = con;
    }

    public void guardarProducto(Producto producto) {
        Integer cantidad = producto.getCantidad();
        Integer cantidadMaxima = 50;

        final Connection con = new ConnectionFactory().recuperaConexion();

        try (this.con) {
            con.setAutoCommit(false);

            final PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO PRODUCTO (nombre, descripcion, cantidad) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            try (statement) {
                do {
                    Integer cantidadParaGuardar = Math.min(cantidad, cantidadMaxima);
                    producto.setCantidad(cantidadParaGuardar);

                    ejecutaRegistro(producto, statement);

                    cantidad -= cantidadParaGuardar;
                } while (cantidad > 0);

                con.commit();
            } catch (SQLException e) {
                con.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void ejecutaRegistro(Producto producto, PreparedStatement statement) throws SQLException {
        statement.setString(1, producto.getNombre());
        statement.setString(2, producto.getDescripcion());
        statement.setInt(3, producto.getCantidad());
        statement.execute();

        final ResultSet resultSet = statement.getGeneratedKeys();

        try (resultSet) {
            while (resultSet.next()) {
                producto.setId(resultSet.getInt(1));
                System.out.println("El ID generado fue: " + producto);
            }
        }
    }

    public List<Producto> listarProducto() {
        try (this.con) {

            List<Producto> resultado = new ArrayList<>(); // Crea una lista para guardar los resultados

            final PreparedStatement statement = con
                    .prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");

            try (statement) {
                statement.execute(); // Ejecuta la consulta en comillas

                final ResultSet resultSet = statement.getResultSet(); // Recupera el resultado de la consulta en un
                                                                      // result set

                try (resultSet) {
                    while (resultSet.next()) { // Mientras haya un siguiente resultado crea añade una fila
                        Producto fila = new Producto(resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getInt(4));
                        resultado.add(fila);
                    }
                }
            }

            return resultado; // Devuelve la lista
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int eliminarProducto(Integer id) {
        try {
            final PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");

            try (statement) {
                statement.setInt(1, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int modificarProducto(Producto producto) {
        try {
            final PreparedStatement statement = con.prepareStatement(
                    "UPDATE PRODUCTO SET "
                            + " NOMBRE = ?, "
                            + " DESCRIPCION = ?,"
                            + " CANTIDAD = ?"
                            + " WHERE ID = ?");

            try (statement) {
                statement.setString(1, producto.getNombre());
                statement.setString(2, producto.getDescripcion());
                statement.setInt(3, producto.getCantidad());
                statement.setInt(4, producto.getId());
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
