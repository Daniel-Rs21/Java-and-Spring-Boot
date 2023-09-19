package com.latam.alura.tienda.vo;

import java.time.LocalDate;

public class ResumenDeVentas {

    private String nombreDelProducto;
    private Long cantidadVendida;
    private LocalDate fechaUltimaVenta;

    public ResumenDeVentas(String nombreDelProducto, Long cantidadVendida, LocalDate fechaUltimaVenta) {
        this.nombreDelProducto = nombreDelProducto;
        this.cantidadVendida = cantidadVendida;
        this.fechaUltimaVenta = fechaUltimaVenta;
    }

    @Override
    public String toString() {
        return "ResumenDeVentas [nombreDelProducto: " + nombreDelProducto + ", cantidadVendida: " + cantidadVendida
                + ", fechaUltimaVenta: " + fechaUltimaVenta + "]";
    }    
}
