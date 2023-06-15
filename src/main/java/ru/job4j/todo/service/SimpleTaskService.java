package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    private TaskRepository taskRepository;

    public Task create(Task task) {
        return taskRepository.create(task);
    }

    public void update(Task task) {
        taskRepository.update(task);
    }

    public void delete(int taskId) {
        taskRepository.delete(taskId);
    }

    public List<Task> findAll() {
        return taskRepository.findAllOrderById();
    }

    public List<Task> findAllDone() {
        return taskRepository.getByDoneOrderById(true);
    }

    public List<Task> findAllNotDone() {
        return taskRepository.getByDoneOrderById(false);
    }

    public Optional<Task> findById(int taskId) {
        return taskRepository.findById(taskId);
    }
}
