package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;


@Repository
@AllArgsConstructor
public class HibernateUserStore implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateUserStore.class.getName());

    private final SessionFactory sf;

    @Override
    public Optional<User> save(User user) {
        Session session = sf.openSession();
        Optional<User> rsl = Optional.empty();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
            rsl = Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("save user", e);
        }
        return rsl;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Optional<User> result = Optional.empty();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery(
                    "from User as u where u.login = :login"
                            + " and u.password = :password", User.class);
            query.setParameter("login", login)
                    .setParameter("password", password);
            result = query.uniqueResultOptional();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("find by login and password", e);
        }
        return result;
    }
}