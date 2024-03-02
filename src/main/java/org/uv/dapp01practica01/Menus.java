package org.uv.dapp01practica01;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author yodoeaoffi06
 */
public class Menus {

    private static final String ACLARACION = ". En caso de no querer modificar este campo, no introduzca nada)";
    private static final String MENSAJE = "Ha ocurrdo un error";
    private static final String TIPO = "Error";
    
    private Menus() {
        throw new IllegalStateException("Utility class");
    }

    public static void menu() {
        int op = 0;
        do {
            op = Integer.parseInt(JOptionPane.showInputDialog(null, "Seleccione una opción:\n"
                    + "1.- Agregar un nuevo empleado.\n"
                    + "2.- Eliminar un empleado.\n"
                    + "3.- Modificar un empleado.\n"
                    + "4.- Buscar un empleado.\n"
                    + "5.- Mostrar todos los empleados.\n"
                    + "6.- Generar una venta.\n"
                    + "7.- Salir."));

            switch (op) {
                case 1:
                    agregar();
                    break;
                case 2:
                    eliminar();
                    break;
                case 3:
                    modificar();
                    break;
                case 4:
                    buscar();
                    break;
                case 5:
                    mostrar();
                    break;
                case 6:
                    generarVenta();
                    break;
                case 7:
                    JOptionPane.showMessageDialog(null, "Hasta luego!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida",
                            TIPO, JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } while (op != 7);
    }

    private static void agregar() {
        PojoEmpleado emp = new PojoEmpleado();
        emp.setNombre(JOptionPane.showInputDialog(null,
                "Introduzca el nombre del empleado"));
        emp.setDireccion(JOptionPane.showInputDialog(null,
                "Introduzca la dirección del empleado"));
        emp.setTelefono(JOptionPane.showInputDialog(null,
                "Introduzca el teléfono del empleado"));

        HDAOEmpleado dao = new HDAOEmpleado();
        dao.guardar(emp);

        JOptionPane.showMessageDialog(null, "Se ha creado un nuevo empleado!");
    }

    private static void eliminar() {
        long id = Long.parseLong(JOptionPane.showInputDialog("Introduzca el ID del usuario a eliminar:"));

        HDAOEmpleado dao = new HDAOEmpleado();
        dao.eliminar(id);

        JOptionPane.showMessageDialog(null, "Se ha eliminado al empleado!");
    }

    private static void modificar() {
        long id = Long.parseLong(JOptionPane.showInputDialog("Introduzca el ID del usuario a modificar:"));

        HDAOEmpleado dao = new HDAOEmpleado();
        PojoEmpleado vemp = dao.buscarById(id);

        PojoEmpleado nemp = new PojoEmpleado();
        nemp.setNombre(JOptionPane.showInputDialog("Introduzca el nuevo nombre:\n\n"
                + "(Nombre actual: " + vemp.getNombre()
                + ACLARACION));
        if (nemp.getNombre().equals("")) {
            nemp.setNombre(vemp.getNombre());
        }
        nemp.setDireccion(JOptionPane.showInputDialog("Introduzca la nueva dirección:\n\n"
                + "(Dirección actual: " + vemp.getDireccion()
                + ACLARACION));
        if (nemp.getDireccion().equals("")) {
            nemp.setDireccion(vemp.getDireccion());
        }
        nemp.setTelefono(JOptionPane.showInputDialog("Introduzca el nuevo teléfono:\n\n"
                + "(Télefono actual: " + vemp.getTelefono()
                + ACLARACION));
        if (nemp.getTelefono().equals("")) {
            nemp.setTelefono(vemp.getTelefono());
        }

        dao.modificar(nemp, id);

        JOptionPane.showMessageDialog(null, "Se ha modificado el empleado!");
    }

    private static void buscar() {
        long id = Long.parseLong(JOptionPane.showInputDialog("Introduzca el ID del usuario por buscar:"));

        HDAOEmpleado dao = new HDAOEmpleado();
        PojoEmpleado emp = dao.buscarById(id);

        if (emp != null) {
            JOptionPane.showMessageDialog(null, "Empleado encontrado!\n"
                    + "ID: " + emp.getId() + ".\n"
                    + "Nombre: " + emp.getNombre() + ".\n"
                    + "Dirección: " + emp.getDireccion() + ".\n"
                    + "Teléfono: " + emp.getTelefono() + ".");
        } else {
            JOptionPane.showMessageDialog(null, MENSAJE,
                    TIPO, JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void mostrar() {
        String cadena = "Empleados:\n";
        HDAOEmpleado dao = new HDAOEmpleado();
        List<PojoEmpleado> emps = dao.buscarAll();

        if (!emps.isEmpty()) {
            for (PojoEmpleado emp : emps) {
                cadena += "ID: " + emp.getId() + ", "
                        + "Nombre: " + emp.getNombre() + ", "
                        + "Dirección: " + emp.getDireccion() + ", "
                        + "Teléfono: " + emp.getTelefono() + ".\n";
            }

            JOptionPane.showMessageDialog(null, "Empleados impresos en consola!");
            Logger.getLogger("Info").info(cadena);
        } else {
            JOptionPane.showMessageDialog(null, MENSAJE,
                    TIPO, JOptionPane.ERROR_MESSAGE);
        }
    }
    private static void generarVenta() {
        int numProductos = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Ingrese el número de productos en la venta:"));

        // Crear una nueva venta
        Venta venta = new Venta();
        venta.setCliente(JOptionPane.showInputDialog(null,
                "Ingrese el nombre del cliente:"));
        venta.setFecha(new Date(System.currentTimeMillis())); // Fecha actual
        venta.setTotal(0); // Total inicializado en 0

        // Guardar la venta en la base de datos
        DAOVenta daoVenta = new DAOVenta();
        venta = daoVenta.guardar(venta);

        // Ingresar los detalles de la venta
        for (int i = 0; i < numProductos; i++) {
            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setIdVenta(venta.getId());
            detalleVenta.setProducto(JOptionPane.showInputDialog(null,
                    "Ingrese el nombre del producto " + (i + 1) + ":"));
            detalleVenta.setCantidad(Double.parseDouble(JOptionPane.showInputDialog(null,
                    "Ingrese la cantidad del producto " + (i + 1) + ":")));
            detalleVenta.setPrecio(Double.parseDouble(JOptionPane.showInputDialog(null,
                    "Ingrese el precio del producto " + (i + 1) + ":")));

            // Guardar el detalle de la venta en la base de datos
            DAODetalleVenta daoDetalleVenta = new DAODetalleVenta();
            daoDetalleVenta.guardar(detalleVenta);

            // Actualizar el total de la venta
            venta.setTotal(venta.getTotal() + detalleVenta.getCantidad() * detalleVenta.getPrecio());
            daoVenta.modificar(venta, venta.getId());
        }

        JOptionPane.showMessageDialog(null, "Venta generada exitosamente!");
    }
}