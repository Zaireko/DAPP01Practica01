package org.uv.dapp01practica01;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author yodoeaoffi06
 */

@Entity
@Table(name = "venta")
public class Venta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venta_idventa_seq")
    @SequenceGenerator(name = "venta_idventa_seq", sequenceName = "venta_idventa_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "idventa")
    private long id;

    @Column
    private String cliente;

    @Column
    private Date fecha;

    @Column
    private double total;
    
    @OneToMany(mappedBy = "venta", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalleVentas;
    
    public Venta() {
        detalleVentas = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<DetalleVenta> getDetalleVentas() {
        return detalleVentas;
    }

    public void setDetalleVentas(List<DetalleVenta> detalleVentas) {
        this.detalleVentas = detalleVentas;
    }
}