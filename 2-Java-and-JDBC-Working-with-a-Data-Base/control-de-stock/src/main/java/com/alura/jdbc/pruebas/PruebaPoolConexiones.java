package com.alura.jdbc.pruebas;

import java.sql.SQLException;

import com.alura.jdbc.factory.ConnectionFactory;

public class PruebaPoolConexiones {
    public static void main(String[] args) throws SQLException {
        
        ConnectionFactory connectionFactory = new ConnectionFactory();

        for (int i = 0; i < 20; i++) {
            connectionFactory.recuperaConexion();
            System.out.println("Conexión número: " + (i+ 1));
        }
    }
}
