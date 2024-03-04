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

    private static final String MENSAJE_ERROR = "Ha ocurrido un error";
    private static final String TIPO_ERROR = "Error";

    private Menus() {
        throw new IllegalStateException("Utility class");
    }

    public static void menu() {
        int opcion;
        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "Seleccione una opción:\n"
                    + "1.- Agregar un nuevo empleado.\n"
                    + "2.- Eliminar un empleado.\n"
                    + "3.- Modificar un empleado.\n"
                    + "4.- Buscar un empleado.\n"
                    + "5.- Mostrar todos los empleados.\n"
                    + "6.- Generar una venta.\n"
                    + "7.- Mostrar todas las ventas.\n"
                    + "8.- Buscar una venta.\n"
                    + "9.- Salir."));

            switch (opcion) {
                case 1:
                    agregarEmpleado();
                    break;
                case 2:
                    eliminarEmpleado();
                    break;
                case 3:
                    modificarEmpleado();
                    break;
                case 4:
                    buscarEmpleado();
                    break;
                case 5:
                    mostrarEmpleados();
                    break;
                case 6:
                    generarVenta();
                    break;
                case 7:
                    mostrarVentas();
                    break;
                case 8:
                    buscarVenta();
                    break;
                case 9:
                    JOptionPane.showMessageDialog(null, "¡Hasta luego!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida",
                            TIPO_ERROR, JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } while (opcion != 9);
    }

    private static void agregarEmpleado() {
        PojoEmpleado empleado = new PojoEmpleado();
        empleado.setNombre(JOptionPane.showInputDialog(null,
                "Introduzca el nombre del empleado"));
        empleado.setDireccion(JOptionPane.showInputDialog(null,
                "Introduzca la dirección del empleado"));
        empleado.setTelefono(JOptionPane.showInputDialog(null,
                "Introduzca el teléfono del empleado"));

        HDAOEmpleado dao = new HDAOEmpleado();
        dao.guardar(empleado);

        JOptionPane.showMessageDialog(null, "¡Se ha creado un nuevo empleado!");
    }

    private static void eliminarEmpleado() {
        long id = Long.parseLong(JOptionPane.showInputDialog("Introduzca el ID del usuario a eliminar:"));

        HDAOEmpleado dao = new HDAOEmpleado();
        dao.eliminar(id);

        JOptionPane.showMessageDialog(null, "¡Se ha eliminado al empleado!");
    }

    private static void modificarEmpleado() {
        long id = Long.parseLong(JOptionPane.showInputDialog("Introduzca el ID del usuario a modificar:"));

        HDAOEmpleado dao = new HDAOEmpleado();
        PojoEmpleado empleadoAnterior = dao.buscarById(id);

        PojoEmpleado empleadoNuevo = new PojoEmpleado();
        empleadoNuevo.setNombre(JOptionPane.showInputDialog("Introduzca el nuevo nombre:\n\n"
                + "(Nombre actual: " + empleadoAnterior.getNombre()
                + " - En caso de no querer modificar este campo, no introduzca nada)"));
        if (empleadoNuevo.getNombre().equals("")) {
            empleadoNuevo.setNombre(empleadoAnterior.getNombre());
        }
        empleadoNuevo.setDireccion(JOptionPane.showInputDialog("Introduzca la nueva dirección:\n\n"
                + "(Dirección actual: " + empleadoAnterior.getDireccion()
                + " - En caso de no querer modificar este campo, no introduzca nada)"));
        if (empleadoNuevo.getDireccion().equals("")) {
            empleadoNuevo.setDireccion(empleadoAnterior.getDireccion());
        }
        empleadoNuevo.setTelefono(JOptionPane.showInputDialog("Introduzca el nuevo teléfono:\n\n"
                + "(Télefono actual: " + empleadoAnterior.getTelefono()
                + " - En caso de no querer modificar este campo, no introduzca nada)"));
        if (empleadoNuevo.getTelefono().equals("")) {
            empleadoNuevo.setTelefono(empleadoAnterior.getTelefono());
        }

        dao.modificar(empleadoNuevo, id);

        JOptionPane.showMessageDialog(null, "¡Se ha modificado el empleado!");
    }

    private static void buscarEmpleado() {
        long id = Long.parseLong(JOptionPane.showInputDialog("Introduzca el ID del usuario por buscar:"));

        HDAOEmpleado dao = new HDAOEmpleado();
        PojoEmpleado empleado = dao.buscarById(id);

        if (empleado != null) {
            JOptionPane.showMessageDialog(null, "Empleado encontrado!\n"
                    + "ID: " + empleado.getId() + ".\n"
                    + "Nombre: " + empleado.getNombre() + ".\n"
                    + "Dirección: " + empleado.getDireccion() + ".\n"
                    + "Teléfono: " + empleado.getTelefono() + ".");
        } else {
            JOptionPane.showMessageDialog(null, MENSAJE_ERROR,
                    TIPO_ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void mostrarEmpleados() {
        String cadena = "Empleados:\n";
        HDAOEmpleado dao = new HDAOEmpleado();
        List<PojoEmpleado> empleados = dao.buscarAll();

        if (!empleados.isEmpty()) {
            for (PojoEmpleado empleado : empleados) {
                cadena += "ID: " + empleado.getId() + ", "
                        + "Nombre: " + empleado.getNombre() + ", "
                        + "Dirección: " + empleado.getDireccion() + ", "
                        + "Teléfono: " + empleado.getTelefono() + ".\n";
            }

            JOptionPane.showMessageDialog(null, "¡Empleados impresos en consola!");
            JOptionPane.showMessageDialog(null, cadena);
        } else {
            JOptionPane.showMessageDialog(null, MENSAJE_ERROR,
                    TIPO_ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void generarVenta() {
        Venta venta = new Venta();

        // Establecer la fecha actual
        venta.setFecha(new Date(System.currentTimeMillis()));

        // Establecer el cliente
        String cliente = JOptionPane.showInputDialog("Introduzca el nombre del cliente:");
        venta.setCliente(cliente);

        // Detalles de la venta
        int cantidadProductos = Integer.parseInt(JOptionPane.showInputDialog("Introduzca el número de productos:"));
        double totalVenta = 0.0;
        for (int i = 0; i < cantidadProductos; i++) {
            DetalleVenta detalleVenta = new DetalleVenta();
            String nombreProducto = JOptionPane.showInputDialog("Introduzca el nombre del producto:");
            detalleVenta.setProducto(nombreProducto);
            double cantidadProducto = Double.parseDouble(JOptionPane.showInputDialog("Introduzca la cantidad del producto:"));
            detalleVenta.setCantidad(cantidadProducto);
            double precioProducto = Double.parseDouble(JOptionPane.showInputDialog("Introduzca el precio del producto:"));
            detalleVenta.setPrecio(precioProducto);

            // Agregar detalleVenta a la venta
            venta.getDetalleVentas().add(detalleVenta);

            // Calcular totalVenta
            totalVenta += cantidadProducto * precioProducto;
        }

        venta.setTotal(totalVenta);

        // Guardar la venta en la base de datos
        DAOVenta dao = new DAOVenta();
        dao.guardar(venta);

        JOptionPane.showMessageDialog(null, "¡Se ha generado una nueva venta!");
    }

    private static void mostrarVentas() {
        String cadena = "Ventas:\n";
        DAOVenta dao = new DAOVenta();
        List<Venta> ventas = dao.buscarAll();

        if (!ventas.isEmpty()) {
            for (Venta venta : ventas) {
                cadena += "ID: " + venta.getId() + ", "
                        + "Cliente: " + venta.getCliente() + ", "
                        + "Fecha: " + venta.getFecha() + ", "
                        + "Total: " + venta.getTotal() + ".\n";
            }

            JOptionPane.showMessageDialog(null, "¡Ventas encontradas!");
            JOptionPane.showMessageDialog(null, cadena);
        } else {
            JOptionPane.showMessageDialog(null, MENSAJE_ERROR,
                    TIPO_ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void buscarVenta() {
        long id = Long.parseLong(JOptionPane.showInputDialog("Introduzca el ID de la venta a buscar:"));

        DAOVenta dao = new DAOVenta();
        Venta venta = dao.buscarById(id);

        if (venta != null) {
            JOptionPane.showMessageDialog(null, "Venta encontrada!\n"
                    + "ID: " + venta.getId() + ".\n"
                    + "Cliente: " + venta.getCliente() + ".\n"
                    + "Fecha: " + venta.getFecha() + ".\n"
                    + "Total: " + venta.getTotal() + ".");
        } else {
            JOptionPane.showMessageDialog(null, MENSAJE_ERROR,
                    TIPO_ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }
}