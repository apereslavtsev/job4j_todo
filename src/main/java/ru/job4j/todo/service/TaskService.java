package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    public Task create(Task task);

    public boolean update(Task task);

    public boolean delete(int taskId);

    public boolean done(int id);

    public List<Task> findAll();

    public List<Task> findAll(String timezone);

    public List<Task> findAllDone();

    public List<Task> findAllDone(String timezone);

    public List<Task> findAllNotDone();

    public List<Task> findAllNotDone(String timezone);

    public Optional<Task> findById(int taskId);

    public Optional<Task> findById(int taskId, String timezone);
    
}
