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
public class DAOVenta implements IDAOGeneral<Venta, Long> {

    @Override
    public Venta guardar(Venta venta) {
        TransanctionDB<Venta> t = new TransanctionDB<Venta>(venta) {
            @Override
            public boolean execute(Connection con) {
                try {
                    String sql = "INSERT INTO venta (cliente, fecha, total) VALUES (?, ?, ?)";
                    try (PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                        pstm.setString(1, venta.getCliente());
                        pstm.setDate(2, venta.getFecha());
                        pstm.setDouble(3, venta.getTotal());
                        pstm.executeUpdate();

                        ResultSet rs = pstm.getGeneratedKeys();
                        if (rs.next()) {
                            venta.setId(rs.getLong(1));
                        }
                    }
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        ConexionDB conexion = ConexionDB.getInstance();
        conexion.execute(t);
        return venta;
    }

    @Override
    public Venta modificar(Venta venta, Long clave) {
        TransanctionDB<Venta> t = new TransanctionDB<Venta>() {
            @Override
            public boolean execute(Connection con) {
                try {
                    String sql = "UPDATE venta SET cliente = ?, fecha = ?, total = ? WHERE idVenta = ?";
                    try (PreparedStatement pstm = con.prepareStatement(sql)) {
                        pstm.setString(1, venta.getCliente());
                        pstm.setDate(2, venta.getFecha());
                        pstm.setDouble(3, venta.getTotal());
                        pstm.setLong(4, venta.getId());
                        pstm.executeUpdate();
                    }
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        ConexionDB conexion = ConexionDB.getInstance();
        conexion.execute(t);
        return venta;
    }

    @Override
    public boolean eliminar(Long clave) {
        TransanctionDB t = new TransanctionDB() {
            @Override
            public boolean execute(Connection con) {
                try {
                    try (PreparedStatement pstm = con.prepareStatement("DELETE FROM venta WHERE idVenta = ?")) {
                        pstm.setLong(1, clave);
                        pstm.executeUpdate();
                    }
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        ConexionDB conexion = ConexionDB.getInstance();
        return conexion.execute(t);
    }

    @Override
    public Venta buscarById(Long id) {
        SelectionDB<Venta> s = new SelectionDB<Venta>(new Venta()) {
            @Override
            public List<Venta> select(Connection con) {
                List<Venta> ventas = new ArrayList<>();
                try {
                    try (PreparedStatement pstm = con.prepareStatement("SELECT * FROM venta WHERE idVenta = ?")) {
                        pstm.setLong(1, id);
                        try (ResultSet rs = pstm.executeQuery()) {
                            if (rs.next()) {
                                Venta venta = new Venta();
                                venta.setId(rs.getLong("idVenta"));
                                venta.setCliente(rs.getString("cliente"));
                                venta.setFecha(rs.getDate("fecha"));
                                venta.setTotal(rs.getDouble("total"));
                                ventas.add(venta);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
                }
                return ventas;
            }
        };
        ConexionDB conexion = ConexionDB.getInstance();
        List<Venta> ventas = conexion.select(s);
        return ventas.isEmpty() ? null : ventas.get(0);
    }

    @Override
    public List<Venta> buscarAll() {
        SelectionDB<Venta> s = new SelectionDB<Venta>(new Venta()) {
            @Override
            public List<Venta> select(Connection con) {
                List<Venta> ventas = new ArrayList<>();
                try {
                    try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM venta")) {
                        while (rs.next()) {
                            Venta venta = new Venta();
                            venta.setId(rs.getLong("idVenta"));
                            venta.setCliente(rs.getString("cliente"));
                            venta.setFecha(rs.getDate("fecha"));
                            venta.setTotal(rs.getDouble("total"));
                            ventas.add(venta);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
                }
                return ventas;
            }
        };
        ConexionDB conexion = ConexionDB.getInstance();
        return conexion.select(s);
    }
}