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
public class DAOEmpleado {

    private static final String URL = "jdbc:postgresql://localhost:5432/ejemplo";
    private static final String USR = "postgres";
    private static final String PWD = "postgres";

    public boolean guardar(PojoEmpleado empleado) {
        try (Connection con = DriverManager.getConnection(URL, USR, PWD)) {
            String sql = "INSERT INTO empleados (nombre, direccion, telefono) VALUES (?, ?, ?)";
            try (PreparedStatement pstm = con.prepareStatement(sql)) {
                pstm.setString(1, empleado.getNombre());
                pstm.setString(2, empleado.getDireccion());
                pstm.setString(3, empleado.getTelefono());
                pstm.executeUpdate();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean eliminar(int id) {
        try (Connection con = DriverManager.getConnection(URL, USR, PWD); PreparedStatement pstm = con.prepareStatement("DELETE FROM empleados WHERE id = ?")) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean modificar(PojoEmpleado empleado, int id) {
        try (Connection con = DriverManager.getConnection(URL, USR, PWD); PreparedStatement pstm = con.prepareStatement("UPDATE empleados SET nombre = ?, direccion = ?, telefono = ? WHERE id = ?")) {
            pstm.setString(1, empleado.getNombre());
            pstm.setString(2, empleado.getDireccion());
            pstm.setString(3, empleado.getTelefono());
            pstm.setInt(4, id);
            pstm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public PojoEmpleado buscarById(int id) {
        try (Connection con = DriverManager.getConnection(URL, USR, PWD); PreparedStatement pstm = con.prepareStatement("SELECT * FROM empleados WHERE id = ?")) {
            pstm.setInt(1, id);
            try (ResultSet reg = pstm.executeQuery()) {
                if (reg.next()) {
                    PojoEmpleado empleado = new PojoEmpleado();
                    empleado.setId(reg.getInt(1));
                    empleado.setNombre(reg.getString(2));
                    empleado.setDireccion(reg.getString(3));
                    empleado.setTelefono(reg.getString(4));
                    return empleado;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<PojoEmpleado> buscarAll() {
        List<PojoEmpleado> empleados = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(URL, USR, PWD); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT id, nombre, direccion, telefono FROM empleados")) {

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

        } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleados;
    }
}
