package org.uv.dapp01practica01;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author yodoeaoffi06
 */

@Entity
@Table(name = "detalleVenta")
public class DetalleVenta implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalleVenta_idLinea_seq")
    @SequenceGenerator(name = "detalleVenta_idLinea_seq", sequenceName = "detalleVenta_idLinea_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "idLinea")
    private long idLinea;
    
    @Column
    private String producto;
    
    @Column
    private double cantidad;
    
    @Column
    private double precio;
    
    @Column
    private long idVenta;

    public long getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(long idLinea) {
        this.idLinea = idLinea;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(long idVenta) {
        this.idVenta = idVenta;
    }
}
