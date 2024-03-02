/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dapp01practica01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author zaireko
 */
public class DAODetalleVenta implements IDAOGeneral<DetalleVenta, Long> {

    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta) {
        TransanctionDB<DetalleVenta> t = new TransanctionDB<DetalleVenta>(detalleVenta) {
            @Override
            public boolean execute(Connection con) {
                try {
                    String sql = "INSERT INTO detalleVenta (producto, cantidad, precio, idVenta) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                        pstm.setString(1, detalleVenta.getProducto());
                        pstm.setDouble(2, detalleVenta.getCantidad());
                        pstm.setDouble(3, detalleVenta.getPrecio());
                        pstm.setLong(4, detalleVenta.getIdVenta());
                        pstm.executeUpdate();

                        ResultSet rs = pstm.getGeneratedKeys();
                        if (rs.next()) {
                            detalleVenta.setIdLinea(rs.getLong(1));
                        }
                    }
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAODetalleVenta.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        ConexionDB conexion = ConexionDB.getInstance();
        conexion.execute(t);
        return detalleVenta;
    }

    @Override
    public DetalleVenta modificar(DetalleVenta detalleVenta, Long clave) {
        TransanctionDB<DetalleVenta> t = new TransanctionDB<DetalleVenta>() {
            @Override
            public boolean execute(Connection con) {
                try {
                    String sql = "UPDATE detalleVenta SET producto = ?, cantidad = ?, precio = ?, idVenta = ? WHERE idLinea = ?";
                    try (PreparedStatement pstm = con.prepareStatement(sql)) {
                        pstm.setString(1, detalleVenta.getProducto());
                        pstm.setDouble(2, detalleVenta.getCantidad());
                        pstm.setDouble(3, detalleVenta.getPrecio());
                        pstm.setLong(4, detalleVenta.getIdVenta());
                        pstm.setLong(5, clave);
                        pstm.executeUpdate();
                    }
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAODetalleVenta.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        ConexionDB conexion = ConexionDB.getInstance();
        conexion.execute(t);
        return detalleVenta;
    }

    @Override
    public boolean eliminar(Long clave) {
        TransanctionDB t = new TransanctionDB() {
            @Override
            public boolean execute(Connection con) {
                try {
                    try (PreparedStatement pstm = con.prepareStatement("DELETE FROM detalleVenta WHERE idLinea = ?")) {
                        pstm.setLong(1, clave);
                        pstm.executeUpdate();
                    }
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAODetalleVenta.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        ConexionDB conexion = ConexionDB.getInstance();
        return conexion.execute(t);
    }

    @Override
    public DetalleVenta buscarById(Long id) {
        SelectionDB<DetalleVenta> s = new SelectionDB<DetalleVenta>(new DetalleVenta()) {
            @Override
            public List<DetalleVenta> select(Connection con) {
                List<DetalleVenta> detalles = new ArrayList<>();
                try {
                    try (PreparedStatement pstm = con.prepareStatement("SELECT * FROM detalleVenta WHERE idLinea = ?")) {
                        pstm.setLong(1, id);
                        try (ResultSet rs = pstm.executeQuery()) {
                            if (rs.next()) {
                                DetalleVenta detalle = new DetalleVenta();
                                detalle.setIdLinea(rs.getLong("idLinea"));
                                detalle.setProducto(rs.getString("producto"));
                                detalle.setCantidad(rs.getDouble("cantidad"));
                                detalle.setPrecio(rs.getDouble("precio"));
                                detalle.setIdVenta(rs.getLong("idVenta"));
                                detalles.add(detalle);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DAODetalleVenta.class.getName()).log(Level.SEVERE, null, ex);
                }
                return detalles;
            }
        };
        ConexionDB conexion = ConexionDB.getInstance();
        List<DetalleVenta> detalles = conexion.select(s);
        return detalles.isEmpty() ? null : detalles.get(0);
    }

    @Override
    public List<DetalleVenta> buscarAll() {
        SelectionDB<DetalleVenta> s = new SelectionDB<DetalleVenta>(new DetalleVenta()) {
            @Override
            public List<DetalleVenta> select(Connection con) {
                List<DetalleVenta> detalles = new ArrayList<>();
                try {
                    try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM detalleVenta")) {
                        while (rs.next()) {
                            DetalleVenta detalle = new DetalleVenta();
                            detalle.setIdLinea(rs.getLong("idLinea"));
                            detalle.setProducto(rs.getString("producto"));
                            detalle.setCantidad(rs.getDouble("cantidad"));
                            detalle.setPrecio(rs.getDouble("precio"));
                            detalle.setIdVenta(rs.getLong("idVenta"));
                            detalles.add(detalle);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DAODetalleVenta.class.getName()).log(Level.SEVERE, null, ex);
                }
                return detalles;
            }
        };
        ConexionDB conexion = ConexionDB.getInstance();
        return conexion.select(s);
    }
}