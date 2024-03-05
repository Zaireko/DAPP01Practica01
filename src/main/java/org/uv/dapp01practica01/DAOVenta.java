package org.uv.dapp01practica01;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author yodoeaoffi06
 */
public class DAOVenta implements IDAOGeneral<Venta, Long> {

    @Override
    public Venta guardar(Venta pojo) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(pojo);
            for (DetalleVenta det : pojo.getDetalleVentas()) {
                session.save(det);
            }

            transaction.commit();
            return pojo;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public Venta modificar(Venta pojo, Long clave) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(pojo);
            transaction.commit();
            return pojo;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean eliminar(Long clave) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            Venta venta = session.get(Venta.class, clave);
            if (venta != null) {
                session.delete(venta);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Venta buscarById(Long id) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            Venta venta = session.get(Venta.class, id);
            return venta;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Venta> buscarAll() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            Query<Venta> query = session.createQuery("FROM Venta", Venta.class);
            List<Venta> ventas = query.getResultList();
            return ventas;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}
