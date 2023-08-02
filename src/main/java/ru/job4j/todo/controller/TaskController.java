package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;
import ru.job4j.todo.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private static final Logger LOG = LoggerFactory.getLogger(TaskController.class.getName());

    private TaskService taskService;

    private PriorityService priorityService;

    private CategoryService categoryService;

    private UserService userService;

    @GetMapping()
    public String getAll(Model model, @SessionAttribute User user) {
        model.addAttribute("tasks", taskService.findAll(
            userService.getUserTimezoneOrDefault(user)));
        return "tasks/list";
    }

    @GetMapping("doneTasks")
    public String getDoneTasks(Model model, @SessionAttribute User user) {
        model.addAttribute("tasks", taskService.findAllDone(
            userService.getUserTimezoneOrDefault(user)));
        return "tasks/list";
    }

    @GetMapping("performedTasks")
    public String getNotDoneTasks(Model model, @SessionAttribute User user) {
        model.addAttribute("tasks", taskService.findAllNotDone(
            userService.getUserTimezoneOrDefault(user)));
        return "tasks/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id, @SessionAttribute User user) {
        Optional<Task> taskOptional;
        try {
            taskOptional = taskService.findById(id, userService.getUserTimezoneOrDefault(user));            
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
            LOG.error(String.format("tasks id %d not found", id));
            return "errors/404";
        }
        Task task = taskOptional.get();
        model.addAttribute("task", task);
        return "tasks/one";
    }

    @PostMapping("/done/{id}")
    public String doneTask(Model model, @PathVariable int id) {
        if (!taskService.done(id)) {
            model.addAttribute("message",
             "Задача с указанным идентификатором не найден");
            LOG.error(String.format("tasks id %d not found", id));
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("update/{id}")
    public String getByIdForUpdate(Model model, @PathVariable int id, @SessionAttribute User user) {
        Optional<Task> taskOptional = taskService.findById(
            id, userService.getUserTimezoneOrDefault(user));
        if (taskOptional.isEmpty()) {
            model.addAttribute("message",
             "Задача с указанным идентификатором не найден");
            LOG.error(String.format("tasks id %d not found", id));
            return "errors/404";
        }
        Task task = taskOptional.get();
        model.addAttribute("task", task);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("priorities", priorityService.getAll());

        return "tasks/update";
    }


    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("priorities", priorityService.getAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model,
                         @SessionAttribute User user,
                         @RequestParam int priorityId,
                         @RequestParam List<Integer> categoriesId) {
        fillTaskFromAttributesData(task, user, priorityId, categoriesId);
        taskService.create(task);
        return "redirect:/tasks";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model,
                         @SessionAttribute User user,
                         @RequestParam int priorityId,
                         @RequestParam List<Integer> categoriesId) {
        fillTaskFromAttributesData(task, user, priorityId, categoriesId);
        if (!taskService.update(task)) {
            model.addAttribute("message", "Не удалось обновить задачу " + task);
            LOG.error("tasks has not been updated" + task);
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    private void fillTaskFromAttributesData(Task task, User user, int priorityId,
                                            List<Integer> categoriesId) {
        task.setUser(user);        
        task.setCategories(categoryService.getByIdList(categoriesId));
        task.setPriority(priorityService.getById(priorityId).get());        
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        if (!taskService.delete(id)) {
            model.addAttribute("message",
             "Не удалось удалить задачу с указанным идентификатором");
            LOG.error(String.format("tasks id %d has not been delete", id));
            return "errors/404";
        }
        return "redirect:/tasks";
    }

}
