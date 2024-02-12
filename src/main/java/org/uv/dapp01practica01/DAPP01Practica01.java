/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.uv.dapp01practica01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author zaireko
 */
public class DAPP01Practica01 {
    public static void main(String[] args) {
        Connection con = null;

        try {
            String url = "jdbc:postgresql://localhost:5432/ejemplo";
            String usr = "postgres";
            String pwd = "postgres";
            con = DriverManager.getConnection(url, usr, pwd);
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, "Se conectó");

            Scanner scanner = new Scanner(System.in);
            int opcion;

            do {
                System.out.println("\nMenú de opciones:");
                System.out.println("1. Insertar empleado");
                System.out.println("2. Mostrar todos los empleados");
                System.out.println("3. Actualizar empleado");
                System.out.println("4. Eliminar empleado");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        insertarEmpleado(con);
                        break;
                    case 2:
                        mostrarEmpleados(con);
                        break;
                    case 3:
                        actualizarEmpleado(con);
                        break;
                    case 4:
                        eliminarEmpleado(con);
                        break;
                    case 5:
                        System.out.println("Saliendo");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } while (opcion != 5);

        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, "No se pudo conectar", ex);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, "Error al cerrar conexión", ex);
                }
            }
        }
    }

    private static void insertarEmpleado(Connection con) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Ingrese el nombre del empleado:");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese la dirección del empleado:");
            String direccion = scanner.nextLine();
            System.out.println("Ingrese el teléfono del empleado:");
            String telefono = scanner.nextLine();

            String sql = "INSERT INTO empleados (nombre, direccion, telefono) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setString(2, direccion);
            pst.setString(3, telefono);
            pst.executeUpdate();
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, "Empleado insertado correctamente.");
        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, "Error al insertar empleado", ex);
        } finally {
            scanner.close();
            System.exit(0);
        }
    }

    private static void mostrarEmpleados(Connection con) {
        try {
            String sql = "SELECT * FROM empleados";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            System.out.println("Lista de empleados:");
            while (rs.next()) {
                System.out.printf("ID: %d, Nombre: %s, Dirección: %s, Teléfono: %s%n",
                        rs.getInt("id"), rs.getString("nombre"), rs.getString("direccion"), rs.getString("telefono"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, "Error al mostrar empleados", ex);
        } finally {
            System.exit(0);
        }
    }

    private static void actualizarEmpleado(Connection con) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Ingrese el ID del empleado que desea actualizar:");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Ingrese el nuevo nombre del empleado:");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese la nueva dirección del empleado:");
            String direccion = scanner.nextLine();
            System.out.println("Ingrese el nuevo teléfono del empleado:");
            String telefono = scanner.nextLine();

            String sql = "UPDATE empleados SET nombre = ?, direccion = ?, telefono = ? WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setString(2, direccion);
            pst.setString(3, telefono);
            pst.setInt(4, id);
            int filasActualizadas = pst.executeUpdate();
            if (filasActualizadas > 0) {
                Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, "Empleado actualizado correctamente.");
            } else {
                Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.WARNING, "No se encontró ningún empleado con ese ID.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, "Error al actualizar empleado", ex);
        } finally {
            scanner.close();
            System.exit(0);
        }
    }

    private static void eliminarEmpleado(Connection con) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Ingrese el ID del empleado que desea eliminar:");
            int id = scanner.nextInt();

            String sql = "DELETE FROM empleados WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            int filasEliminadas = pst.executeUpdate();
            if (filasEliminadas > 0) {
                Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, "Empleado eliminado correctamente.");
            } else {
                Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.WARNING, "No se encontró ningún empleado con ese ID.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, "Error al eliminar empleado", ex);
        } finally {
            scanner.close();
            System.exit(0);
        }
    }
}