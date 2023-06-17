package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    public Task create(Task task);

    public boolean update(Task task);

    public boolean done(int id);

    public boolean delete(int taskId);

    public List<Task> findAllOrderById();

    public List<Task> getByDoneOrderById(Boolean done);

    public Optional<Task> findById(int taskId);

}
