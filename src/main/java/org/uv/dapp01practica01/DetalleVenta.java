package org.uv.dapp01practica01;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author yodoeaoffi06
 */

@Entity
@Table(name = "detalleventa")
public class DetalleVenta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalleventa_idlinea_seq")
    @SequenceGenerator(name = "detalleventa_idlinea_seq", sequenceName = "detalleventa_idlinea_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "idlinea")
    private long id;

    @Column
    private String producto;

    @Column
    private double cantidad;

    @Column
    private double precio;

    @ManyToOne
    @JoinColumn(name = "idventa")
    private Venta venta;
    
    public Venta getVenta(){
        return venta;
    }

    public void setVenta(Venta venta){
        this.venta = venta;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}

