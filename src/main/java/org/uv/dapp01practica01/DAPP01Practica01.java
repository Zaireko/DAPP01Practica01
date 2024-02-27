/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.uv.dapp01practica01;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author zaireko
 */
public class DAPP01Practica01 {
    public static void main(String[] args) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        PojoEmpleado empleado = new PojoEmpleado();
        empleado.setNombre("Galicia Hernandez Hernandez");
        empleado.setDireccion("Calle 89");
        empleado.setTelefono("1234567890");
        
        Session session = sf.getCurrentSession();
        Transaction transacition = session.beginTransaction();
        session.save(empleado);
        transacition.commit();
        System.out.println("Guardao' con id " + empleado.getId());
        
        //Menus.menu();
    }
}