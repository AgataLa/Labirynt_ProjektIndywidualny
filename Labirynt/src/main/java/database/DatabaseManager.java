package database;

import GUIComponents.DIFFICULTY;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class DatabaseManager {

    private static SessionFactory sessionFactory;
    private static Session session;

    public static void initSessionFactory() {
        if (sessionFactory == null) {
            getSessionFactory();
        }
    }

    public static List<? extends Result> getResults(DIFFICULTY difficulty) {
        List<? extends Result> resultList = new ArrayList<>();

        try {
            session = sessionFactory.openSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Result> query = cb.createQuery(Result.class);
            Root<? extends Result> entity = null;

            switch (difficulty) {
                case EASY:
                    entity = query.from(EasyResult.class);
                    break;
                case MEDIUM:
                    entity = query.from(MediumResult.class);
                    break;
                case HARD:
                    entity = query.from(HardResult.class);
                    break;
            }

            CriteriaQuery<? extends Result> selectSort = query.select(entity).orderBy(cb.asc(entity.get("time")));
            TypedQuery<? extends Result> resultQuery = session.createQuery(selectSort);
            resultList = resultQuery.getResultList();
        } catch (Exception e) {
            return resultList;
        } finally {
            try {
                session.close();
            } catch (NullPointerException e) {
            }
        }
        return resultList;
    }

    public static void addResult(DIFFICULTY difficulty, String nickname, long time) {
        Time resultTime = new Time(time);
        if(time < 3600000) {
            resultTime.setHours(0);
        }
        Result result = null;

        switch (difficulty) {
            case EASY:
                result = new EasyResult();
                break;
            case MEDIUM:
                result = new MediumResult();
                break;
            case HARD:
                result = new HardResult();
                break;
        }

        result.setNickname(nickname);
        result.setTime(resultTime);

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(result);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    private static void getSessionFactory() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        List<String> loggers = Collections.list(LogManager.getLogManager().getLoggerNames());
        for ( String logger : loggers ) {
            LogManager.getLogManager().getLogger(logger).setLevel(Level.OFF);
        }

        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(EasyResult.class);
        configuration.addAnnotatedClass(MediumResult.class);
        configuration.addAnnotatedClass(HardResult.class);
        StandardServiceRegistryBuilder registryBuilder =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

        sessionFactory = configuration.buildSessionFactory(registryBuilder.build());

    }
}
