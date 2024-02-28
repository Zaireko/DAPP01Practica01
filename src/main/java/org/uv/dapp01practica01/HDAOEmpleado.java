package org.uv.dapp01practica01;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author yodoeaoffi06
 */
public class HDAOEmpleado implements IDAOGeneral<PojoEmpleado, Long> {

    @Override
    public PojoEmpleado guardar(PojoEmpleado pojo) {
        SessionFactory sf = HibernateUtil.getSessionFactory();

        Session session = sf.getCurrentSession();
        Transaction transacition = session.beginTransaction();
        session.save(pojo);
        transacition.commit();

        return pojo;
    }

    @Override
    public PojoEmpleado modificar(PojoEmpleado pojo, Long clave) {
        SessionFactory sf = HibernateUtil.getSessionFactory();

        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        PojoEmpleado empleado = session.get(PojoEmpleado.class, clave);

        if (empleado != null) {
            
            empleado.setNombre(pojo.getNombre());
            empleado.setDireccion(pojo.getDireccion());
            empleado.setTelefono(pojo.getTelefono());
            session.update(empleado);

            transaction.commit();

            return empleado;
        } else {
            
            transaction.rollback();
            return null;
        }
    }

    @Override
    public boolean eliminar(Long clave) {
        SessionFactory sf = HibernateUtil.getSessionFactory();

        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        PojoEmpleado empleado = session.get(PojoEmpleado.class, clave);

        if (empleado != null) {

            session.delete(empleado);
            transaction.commit();
            return true;
        } else {

            transaction.rollback();
            return false;
        }
    }

    @Override
    public PojoEmpleado buscarById(Long id) {
        SessionFactory sf = HibernateUtil.getSessionFactory();

        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        PojoEmpleado empleado = session.get(PojoEmpleado.class, id);

        transaction.commit();

        return empleado;
    }

    @Override
    public List<PojoEmpleado> buscarAll() {
        SessionFactory sf = HibernateUtil.getSessionFactory();

        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        List<PojoEmpleado> empleados = session.createQuery("FROM empleadosuv", PojoEmpleado.class).getResultList();

        transaction.commit();

        return empleados;
    }

}
