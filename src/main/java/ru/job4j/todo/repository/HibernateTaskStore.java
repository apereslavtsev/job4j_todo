package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskStore implements TaskRepository {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateTaskStore.class.getName());

    private final CrudRepository crudRepository;

    @Override
    public Task create(Task task) {
        try {
            crudRepository.run(session -> session.save(task));
        } catch (Exception e) {
            LOG.error("save tasks", e);
        }
        return task;
    }

    @Override
    public boolean update(Task task) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(task));
            rsl = true;
        } catch (Exception e) {
            LOG.error("update tasks", e);
        }
        return rsl;
    }

    @Override
    public boolean done(int id) {
        boolean rsl = false;
        try {
            rsl = crudRepository.run("update Task set done = :done where id = :id",
                    Map.of("done", true, "id", id)) > 0;
        } catch (Exception e) {
            LOG.error("done tasks", e);
        }
        return rsl;
    }

    @Override
    public boolean delete(int taskId) {
        boolean rsl = false;
        try {
            rsl = crudRepository.run("delete from Task where id =:id",
                    Map.of("id", taskId)) > 0;
        } catch (Exception e) {
            LOG.error("delete task", e);
        }
        return rsl;
    }

    @Override
    public List<Task> findAllOrderById() {
        List<Task> result = List.of();
        try {
            result = crudRepository.query("from Task order by id", Task.class);
        } catch (Exception e) {
            LOG.error("find all tasks", e);
        }
        return result;
    }

    @Override
    public List<Task> getByDoneOrderById(Boolean done) {
        List<Task> result = List.of();
        try {
            result = crudRepository.query("from Task as t where t.done = :fDone order by id",
                    Task.class, Map.of("fDone", done));
        } catch (Exception e) {
            LOG.error("find tasks by done", e);
        }
        return result;
    }

    @Override
    public Optional<Task> findById(int taskId) {

        return crudRepository.optional(
                "from Task where id = :fId", Task.class,
                Map.of("fId", taskId)
        );

    }

}
