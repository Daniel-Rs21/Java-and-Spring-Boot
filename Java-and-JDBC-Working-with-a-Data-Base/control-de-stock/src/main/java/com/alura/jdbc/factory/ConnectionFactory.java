package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
    public Connection recuperaConexion() throws SQLException {
        // TODO

        return DriverManager.getConnection(
		 "jdbc:mysql://localhost:3306/control_de_stock?useTimezone=true&serverTimezone=UTC",
         "root",
         "Dn3l0ck_2107");
    }
}
