package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
       Session session = Util.getSession();
       Transaction transaction = null;
       final String SQL = "CREATE TABLE IF NOT EXISTS users (Id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT," + "name VARCHAR(30), lastname VARCHAR(30), age TINYINT)";
       try {
           transaction = session.beginTransaction();
           session.createSQLQuery(SQL).executeUpdate();
           transaction.commit();
           System.out.println("Table has been created");
       } catch (HibernateException e) {
               transaction.rollback();
           e.printStackTrace();
       }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
            System.out.println("Table has been deleted");
        } catch (HibernateException e) {
                transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
       Session session = Util.getSession();
       Transaction transaction = null;
       try {
           transaction = session.beginTransaction();
           session.save(new User(name, lastName, age));
           transaction.commit();
           System.out.println("User: " + name + " " + lastName + " has been added");
       } catch (HibernateException e) {
               transaction.rollback();
           e.printStackTrace();
       }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createQuery("delete User where id = " + id).executeUpdate();
            transaction.commit();
            System.out.println("User id" + id + " has been deleted");
        } catch (HibernateException e) {
                transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
       List list = new ArrayList<>();
       Session session = Util.getSession();
       Transaction transaction = null;
       try {
           transaction = session.beginTransaction();
           list = session.createQuery("FROM " + User.class.getSimpleName()).list();
           transaction.commit();
       } catch (HibernateException e) {
           transaction.rollback();
           e.printStackTrace();
       }
        return list;
    }

    @Override
    public void cleanUsersTable() {
       Session session = Util.getSession();
       Transaction transaction = null;
       try {
           transaction = session.beginTransaction();
           session.createSQLQuery("Truncate Table Users").executeUpdate();
           transaction.commit();
           System.out.println("Table has been cleaned");
       } catch (HibernateException e) {
               transaction.rollback();
           e.printStackTrace();
       }
    }
}
