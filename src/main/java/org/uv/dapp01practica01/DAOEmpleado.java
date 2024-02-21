/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dapp01practica01;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yodoeaoffi06
 */
public class DAOEmpleado implements IDAOGeneral<PojoEmpleado, Integer> {

    @Override
    public PojoEmpleado guardar(PojoEmpleado pojo) {
        TransanctionDB<PojoEmpleado> t = new TransanctionDB<PojoEmpleado>(pojo) {
            @Override
            public boolean execute(Connection con) {
                try {
                    String sql = "INSERT INTO empleado (nombre, direccion, telefono) VALUES (?, ?, ?)";
                    try (PreparedStatement pstm = con.prepareStatement(sql)) {
                        pstm.setString(1, p.getNombre());
                        pstm.setString(2, p.getDireccion());
                        pstm.setString(3, p.getTelefono());
                        pstm.execute();
                    }
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        ConexionDB conexion = ConexionDB.getInstance();
        conexion.execute(t);
        return pojo;
    }

    @Override
    public PojoEmpleado modificar(PojoEmpleado pojo, Integer clave) {
        TransanctionDB<PojoEmpleado> t = new TransanctionDB<PojoEmpleado>() {
            @Override
            public boolean execute(Connection con) {
                try {
                    String sql = "UPDATE empleado SET nombre = ?, direccion = ?, telefono = ? WHERE id = ?";
                    try (PreparedStatement pstm = con.prepareStatement(sql)) {
                        pstm.setString(1, pojo.getNombre());
                        pstm.setString(2, pojo.getDireccion());
                        pstm.setString(3, pojo.getTelefono());
                        pstm.setInt(4, clave);
                        pstm.executeUpdate();
                    }
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        ConexionDB conexion = ConexionDB.getInstance();
        conexion.execute(t);
        return pojo;
    }

    @Override
    public boolean eliminar(Integer clave) {
        TransanctionDB t = new TransanctionDB() {
            @Override
            public boolean execute(Connection con) {
                try {
                    try (PreparedStatement pstm = con.prepareStatement("DELETE FROM empleado WHERE id = ?")) {
                        pstm.setInt(1, clave);
                        pstm.executeUpdate();
                    }
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        ConexionDB conexion = ConexionDB.getInstance();
        return conexion.execute(t);
    }

    @Override
    public PojoEmpleado buscarById(Integer id) {
        SelectionDB<PojoEmpleado> s = new SelectionDB<PojoEmpleado>(new PojoEmpleado()) {
            @Override
            public List<PojoEmpleado> select(Connection con) {
                List<PojoEmpleado> empleados = new ArrayList<>();
                try {
                    try (PreparedStatement pstm = con.prepareStatement("SELECT * FROM empleado WHERE id = ?")) {
                        pstm.setInt(1, id);
                        try (ResultSet rs = pstm.executeQuery()) {
                            if (rs.next()) {
                                PojoEmpleado empleado = new PojoEmpleado();
                                empleado.setId(rs.getInt("id"));
                                empleado.setNombre(rs.getString("nombre"));
                                empleado.setDireccion(rs.getString("direccion"));
                                empleado.setTelefono(rs.getString("telefono"));
                                empleados.add(empleado);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
                return empleados;
            }
        };
        ConexionDB conexion = ConexionDB.getInstance();
        List<PojoEmpleado> empleados = conexion.select(s);
        return empleados.isEmpty() ? null : empleados.get(0);
    }

    @Override
    public List<PojoEmpleado> buscarAll() {
        SelectionDB<PojoEmpleado> s = new SelectionDB<PojoEmpleado>(new PojoEmpleado()) {
            @Override
            public List<PojoEmpleado> select(Connection con) {
                List<PojoEmpleado> empleados = new ArrayList<>();
                try {
                    try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT id, nombre, direccion, telefono FROM empleado")) {
                        while (rs.next()) {
                            int id = rs.getInt("id");
                            String nombre = rs.getString("nombre");
                            String direccion = rs.getString("direccion");
                            String telefono = rs.getString("telefono");

                            PojoEmpleado empleado = new PojoEmpleado();
                            empleado.setId(id);
                            empleado.setNombre(nombre);
                            empleado.setDireccion(direccion);
                            empleado.setTelefono(telefono);
                            empleados.add(empleado);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
                return empleados;
            }
        };
        ConexionDB conexion = ConexionDB.getInstance();
        return conexion.select(s);
    }
}