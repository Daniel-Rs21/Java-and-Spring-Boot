package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.nimbus.State;

import com.alura.jdbc.factory.ConnectionFactory;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer id, Integer cantidad) throws SQLException {
		final Connection con = new ConnectionFactory().recuperaConexion(); // Abre la conexión

		try (con) {
			final PreparedStatement statement = con
					.prepareStatement("UPDATE PRODUCTO SET NOMBRE = ?, DESCRIPCION = ? , CANTIDAD = ? WHERE ID = ?");

			try (statement) {
				statement.setString(1, nombre);
				statement.setString(2, descripcion);
				statement.setInt(3, cantidad);
				statement.setInt(4, id);
				statement.execute(); // Ejecuta la consulta en comillas

				int updateCount = statement.getUpdateCount();

				con.close(); // Cierra la conexión

				return updateCount;
			}
		}

	}

	public int eliminar(Integer id) throws SQLException {
		final Connection con = new ConnectionFactory().recuperaConexion(); // Abre la conexión

		try (con) {
			final PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");

			try (statement) {
				statement.setInt(1, id);
				statement.execute(); // Ejecuta la consulta en comillas

				int updateCount = statement.getUpdateCount();
				return updateCount; // Devuelve el número de filas afectadas (1 en este caso)
			}
		}
	}

	public List<Map<String, String>> listar() throws SQLException {
		final Connection con = new ConnectionFactory().recuperaConexion(); // Abre la conexión

		try (con) {
			final PreparedStatement statement = con
					.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");

			try (statement) {
				statement.execute(); // Ejecuta la consulta en comillas

				ResultSet resultSet = statement.getResultSet(); // Recupera el resultado de la consulta en un result set

				List<Map<String, String>> resultado = new ArrayList<>(); // Crea una lista de mapas

				while (resultSet.next()) { // Mientras haya un siguiente resultado introduce los datos en un mapa
					Map<String, String> fila = new HashMap<>();
					fila.put("ID", String.valueOf((resultSet.getInt("ID"))));
					fila.put("NOMBRE", resultSet.getString(("NOMBRE")));
					fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
					fila.put("CANTIDAD", String.valueOf((resultSet.getInt("CANTIDAD"))));
					resultado.add(fila); // Añade el mapa a la lista
				}

				return resultado; // Devuelve la lista
			}
		}
	}

	public void guardar(HashMap<String, String> producto) throws SQLException {
		String nombre = producto.get("NOMBRE");
		String descripcion = producto.get("DESCRIPCION");
		Integer cantidad = Integer.valueOf(producto.get("CANTIDAD"));
		Integer cantidadMaxima = 50;

		final Connection con = new ConnectionFactory().recuperaConexion();

		try (con) {
			con.setAutoCommit(false);

			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO PRODUCTO (nombre, descripcion, cantidad) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			try (statement) {
				do {
					Integer cantidadParaGuardar = Math.min(cantidad, cantidadMaxima);

					ejecutaRegistro(nombre, descripcion, cantidadParaGuardar, statement);

					cantidad -= cantidadParaGuardar;
				} while (cantidad > 0);

				con.commit();
			} catch (Exception e) {
				con.rollback();
			}
		}
	}

	private void ejecutaRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement statement)throws SQLException {
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidad);
		statement.execute();

		final ResultSet resultSet = statement.getGeneratedKeys();

		try (resultSet) {
			while (resultSet.next()) {
				System.out.println("El ID generado fue: " + resultSet.getInt(1));
			}
		}
	}

}
