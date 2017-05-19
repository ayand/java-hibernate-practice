package com.ayandas.hibernatepractice;

import java.util.List;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class GetPokemon {
    public static void main(String[] args) {
        try {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            List pokemon = session.createQuery("FROM Pokemon").list();
            for (Iterator iterator = pokemon.iterator(); iterator.hasNext();){
                Pokemon p = (Pokemon) iterator.next();
                System.out.println(p.getSpecies());
            }
            tx.commit();
            session.close();
            factory.close();
        } catch (HibernateException e) {
            System.out.println("Trouble with creating SessionFactory");
            e.printStackTrace();
        }
    }
}
