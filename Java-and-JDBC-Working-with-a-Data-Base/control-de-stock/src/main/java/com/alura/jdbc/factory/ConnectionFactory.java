package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {

    private DataSource dataSource;

    public ConnectionFactory() {
        var pooledDataSource = new ComboPooledDataSource();
        pooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/control_de_stock?useTimezone=true&serverTimezone=UTC");
        pooledDataSource.setUser("root");
        pooledDataSource.setPassword("Dn3l0ck_2107");
        pooledDataSource.setMaxPoolSize(10);

        this.dataSource = pooledDataSource;
    }
    
    public Connection recuperaConexion() {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
