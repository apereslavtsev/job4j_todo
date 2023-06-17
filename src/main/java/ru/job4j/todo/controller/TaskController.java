package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private static final Logger LOG = LoggerFactory.getLogger(TaskController.class.getName());

    private TaskService taskService;

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @GetMapping("doneTasks")
    public String getDoneTasks(Model model) {
        model.addAttribute("tasks", taskService.findAllDone());
        return "tasks/list";
    }

    @GetMapping("performedTasks")
    public String getNotDoneTasks(Model model) {
        model.addAttribute("tasks", taskService.findAllNotDone());
        return "tasks/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        Optional<Task> task = taskService.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Задача с указанным идентификатором не найден");
            LOG.error(String.format("tasks id %d not found", id));
            return "errors/404";
        }
        model.addAttribute("task", task.get());
        return "tasks/one";
    }

    @PostMapping("/done/{id}")
    public String doneTask(Model model, @PathVariable int id) {
        if (!taskService.done(id)) {
            model.addAttribute("message", "Задача с указанным идентификатором не найден");
            LOG.error(String.format("tasks id %d not found", id));
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("update/{id}")
    public String getByIdForUpdate(Model model, @PathVariable int id) {
        Optional<Task> task = taskService.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Задача с указанным идентификатором не найден");
            LOG.error(String.format("tasks id %d not found", id));
            return "errors/404";
        }
        model.addAttribute("task", task.get());
        return "tasks/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model) {
        if(!taskService.update(task)) {
            model.addAttribute("message", "Не удалось обновить задачу " + task);
            LOG.error("tasks has not been updated" + task);
            return "errors/404";
        }
        taskService.update(task);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        if(!taskService.delete(id)) {
            model.addAttribute("message", "Не удалось удалить задачу с указанным идентификатором");
            LOG.error(String.format("tasks id %d has not been delete", id));
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/create")
    public String getCreatePage() {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model) {
            taskService.create(task);
            return "redirect:/tasks";
    }

}
