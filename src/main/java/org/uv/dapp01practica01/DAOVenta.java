/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dapp01practica01;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author zaireko
 */
public class DAOVenta implements IDAOGeneral<Venta, Long> {

    @Override
    public Venta guardar(Venta pojo) {
        try (SessionFactory sf = HibernateUtil.getSessionFactory();
             Session session = sf.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            
            session.save(pojo);
            for (DetalleVenta det : pojo.getDetalleVentas()) {
                session.save(det);
            }
            
            transaction.commit();
            return pojo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Venta modificar(Venta pojo, Long clave) {
        try (SessionFactory sf = HibernateUtil.getSessionFactory();
             Session session = sf.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            
            session.update(pojo);
            transaction.commit();
            
            return pojo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean eliminar(Long clave) {
        try (SessionFactory sf = HibernateUtil.getSessionFactory();
             Session session = sf.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            
            Venta venta = session.get(Venta.class, clave);
            if (venta != null) {
                session.delete(venta);
                transaction.commit();
                return true;
            }
            
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Venta buscarById(Long id) {
        try (SessionFactory sf = HibernateUtil.getSessionFactory();
             Session session = sf.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            
            Venta venta = session.get(Venta.class, id);
            
            transaction.commit();
            
            return venta;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Venta> buscarAll() {
        try (SessionFactory sf = HibernateUtil.getSessionFactory();
             Session session = sf.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            
            Query<Venta> query = session.createQuery("FROM Venta", Venta.class);
            List<Venta> ventas = query.getResultList();
            
            transaction.commit();
            
            return ventas;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
