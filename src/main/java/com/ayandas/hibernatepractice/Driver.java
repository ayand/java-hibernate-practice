package com.ayandas.hibernatepractice;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;

public class Driver {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please supply at least two arguments");
            System.exit(0);
        }
        if (args[0].equals("-p")) {
            int id = Integer.parseInt(args[1]);
            Pokemon p = getPokemon(id);
            System.out.println(p);
        } else if (args[0].equals("-r")) {
            int userID = Integer.parseInt(args[1]);
            int pokemonID = Integer.parseInt(args[2]);
            try {
                Scanner scanner= new Scanner(new File(args[3]));
                String review = scanner.nextLine();
                Integer newID = postReview(userID, pokemonID, review);
                System.out.println("New Review ID: " + newID);
            } catch (FileNotFoundException f) {
                f.printStackTrace();
                System.exit(0);
            }

        } else if (args[0].equals("-t")) {
            if (args.length == 2) {
                List<Matchup> matchups = getWeaknesses(args[1]);
                for (Matchup m: matchups) {
                    System.out.println(m);
                }
            } else {
                List<Matchup> matchups = getWeaknesses(args[1], args[2]);
                for (Matchup m: matchups) {
                    System.out.println(m);
                }
            }
        } else if (args[0].equals("-u")) {
            int pokemonID = Integer.parseInt(args[1]);
            String stat = args[2];
            int newStat = Integer.parseInt(args[3]);
            Pokemon updatedPokemon = changePokemonStat(pokemonID, stat, newStat);
            Pokemon p = getPokemon(pokemonID);
            System.out.println(p);
        }
    }

    public static Pokemon getPokemon(int id) {
        Pokemon p = null;
        try {
            SessionFactory factory = new Configuration().configure()
                .buildSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            p = (Pokemon) session.createCriteria(Pokemon.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
            //return p;
            tx.commit();
            session.close();
            factory.close();
        } catch (Exception e) {
            e.printStackTrace();
            //System.exit(0);
        } finally {
            return p;
        }
    }

    public static List<Matchup> getWeaknesses(String ... types) {
        ArrayList<String> defendingTypes = new ArrayList<>();
        for (String type: types) {
            defendingTypes.add(type);
        }
        List<Matchup> result = new ArrayList<>();
        try {
            SessionFactory factory = new Configuration().configure()
                .buildSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            Criteria c = session.createCriteria(Matchup.class);
            c = c.add(Restrictions.eq("defenseType1", defendingTypes.get(0)));
            if (defendingTypes.size() == 2) {
                c = c.add(Restrictions.eq("defenseType2", defendingTypes.get(1)));
            } else {
                c = c.add(Restrictions.eq("defenseType2", ""));
            }
            c = c.add(Restrictions.gt("effect", 1.0));
            List l = c.list();
            for (Object m: l) {
                Matchup mu = (Matchup) m;
                result.add(mu);
            }
            //result = (List<Matchup>) c.list();
            //return p;
            tx.commit();
            session.close();
            factory.close();
        } catch (Exception e) {
            e.printStackTrace();
            //System.exit(0);
        } finally {
            return result;
        }
    }

    public static Integer postReview(int userID, int pokemonID, String review) {
        Integer i = 0;
        try {
            SessionFactory factory = new Configuration().configure()
                .buildSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            Review r = new Review(userID, pokemonID, review);
            i = (Integer) session.save(r);
            tx.commit();
            session.close();
            factory.close();
        } catch (Exception e) {
            e.printStackTrace();
            //System.exit(0);
        } finally {
            return i;
        }
    }

    public static Pokemon changePokemonStat(int pokemonID, String stat, int newStat) {
        Pokemon p = null;
        try {
            SessionFactory factory = new Configuration().configure()
                .buildSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            Pokemon pokemon = (Pokemon) session.createCriteria(Pokemon.class)
                .add(Restrictions.eq("id", pokemonID))
                .uniqueResult();
            switch (stat) {
                case "attack":
                    pokemon.setAttack(newStat);
                    break;
                case "defense":
                    pokemon.setDefense(newStat);
                    break;
                case "spattack":
                    pokemon.setSpattack(newStat);
                    break;
                case "spdefense":
                    pokemon.setSpdefense(newStat);
                    break;
                case "speed":
                    pokemon.setSpeed(newStat);
                    break;
                case "hp":
                    pokemon.setHp(newStat);
                    break;
            }
            session.update(pokemon);
            p = pokemon;
            tx.commit();
            session.close();
            factory.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return p;
        }
    }
}
