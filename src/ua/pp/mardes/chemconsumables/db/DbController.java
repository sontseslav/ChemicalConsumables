package ua.pp.mardes.chemconsumables.db;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ua.pp.mardes.chemconsumables.model.Consumable;


/**
 * Created by coder on 04.02.17.
 * Used to store/retrieve data to DB
 *
 * for every operation - separate object
 * session always closed after operation
 *
 * later it may be replaced by handling closing
 * in finalize() method or by separate method within
 * object of this class
 */
public class DbController {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    /**
     * creating session factory with given configuration
     */
    private void initFactory(){
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    /**
     *
     * @return session object
     */
    private Session getSession(){
        if (session == null){
            initFactory();
        }
        return sessionFactory.openSession();
    }

    /**
     *
     * @param consumable object
     * @return true in success false otherwise
     * session always closed
     */
    public boolean storeConsumable(Consumable consumable){
        boolean result = false;
        try{
            session = getSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(consumable);
            session.flush();
            transaction.commit();
            result = true;
        }catch (HibernateException e){
            e.printStackTrace();
            if (transaction != null){
                transaction.rollback();
            }
        }finally {
            if (session != null){
                session.close();
            }
            if (sessionFactory != null){
                sessionFactory.close();
            }
        }
        return result;
    }

    public boolean deleteConsumable(Consumable consumable){
        boolean result = false;
        try{
            session = getSession();
            transaction = session.beginTransaction();
            session.delete(consumable);
            session.flush();
            transaction.commit();
            result = true;
        }catch (HibernateException e){
            e.printStackTrace();
            if (transaction != null){
                transaction.rollback();
            }
        }finally {
            if(session != null){
                session.close();
            }
            if (sessionFactory != null){
                sessionFactory.close();
            }
        }
        return result;
    }

    public List<Consumable> retrieveConsumableList(){
        List<Consumable> result = null;
        try{
            session = getSession();
            transaction = session.beginTransaction();
            result = session.createQuery("from Consumable ").list();
        }catch (HibernateException e){
            e.printStackTrace();
            if (transaction != null){
                transaction.rollback();
            }
        }finally {
            if (session != null){
                session.close();
            }
            if (sessionFactory != null){
                sessionFactory.close();
            }
        }
        return result;
    }
}
