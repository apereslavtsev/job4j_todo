package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;
import ru.job4j.todo.util.TimeZoneUtils;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    
    private TaskRepository taskRepository;

    @Override
    public Task create(Task task) {
        return taskRepository.create(task);
    }

    @Override
    public boolean update(Task task) {
        return taskRepository.update(task);
    }

    @Override
    public boolean delete(int taskId) {
        return taskRepository.delete(taskId);
    }

    @Override
    public boolean done(int id) {
        return taskRepository.done(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAllOrderById();
    }

    @Override
    public List<Task> findAllDone() {
        return taskRepository.getByDoneOrderById(true);
    }

    @Override
    public List<Task> findAllNotDone() {
        return taskRepository.getByDoneOrderById(false);
    }

    @Override
    public Optional<Task> findById(int taskId) {
        return taskRepository.findById(taskId);
    }
    
    @Override
    public List<Task> findAll(String timezone) {        
        return TimeZoneUtils.convertTaskListTimeFromTimeZone(findAll(), timezone);
    }

    @Override
    public List<Task> findAllDone(String timezone) {
        return TimeZoneUtils.convertTaskListTimeFromTimeZone(findAllDone(), timezone);
    }

    @Override
    public List<Task> findAllNotDone(String timezone) {
        return TimeZoneUtils.convertTaskListTimeFromTimeZone(findAllNotDone(), timezone);
    }

    @Override
    public Optional<Task> findById(int taskId, String timezone) { 
        var task = findById(taskId);        
        TimeZoneUtils.convertTaskTimeFromTimezone(task.get(), timezone);    
        return task;
    }

}
