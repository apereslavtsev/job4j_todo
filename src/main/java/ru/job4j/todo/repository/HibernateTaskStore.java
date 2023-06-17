package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskStore implements TaskRepository {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateTaskStore.class.getName());

    private final SessionFactory sf;

    @Override
    public Task create(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("save tasks", e);
        }
        return task;
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
            session.close();
            rsl = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("update tasks", e);
        }
        return rsl;
    }

    @Override
    public boolean done(int id) {
        Session session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            rsl = session.createQuery(
                    "update Task set done = :doneParam where id = :idParam")
                    .setParameter("doneParam", true)
                    .setParameter("idParam" ,id)
                    .executeUpdate() > 0;
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("done tasks", e);
        }
        return rsl;
    }

    @Override
    public boolean delete(int taskId) {
        Session session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            var task = new Task();
            task.setId(taskId);
            session.delete(task);
            session.getTransaction().commit();
            session.close();
            rsl = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("delete tasks", e);
        }
        return rsl;
    }

    @Override
    public List<Task> findAllOrderById() {
        Session session = sf.openSession();
        List<Task> result = List.of();
        try {
            session.beginTransaction();
            result = session.createQuery("from Task order by id", Task.class).list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("find all tasks", e);
        }
        return result;
    }

    @Override
    public List<Task> getByDoneOrderById(Boolean done) {
        Session session = sf.openSession();
        List<Task> result = List.of();
        try {
            session.beginTransaction();
            result = session.createQuery(
                            "from Task as t where t.done = :fDone order by id", Task.class)
                    .setParameter("fDone", done).list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("find tasks by done", e);
        }
        return result;
    }

    @Override
    public Optional<Task> findById(int taskId) {
        Session session = sf.openSession();
        Optional<Task> result = Optional.empty();
        try {
            session.beginTransaction();
            result = Optional.of(session.get(Task.class, taskId));
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("find tasks by id", e);
        }
        return result;
    }

}
