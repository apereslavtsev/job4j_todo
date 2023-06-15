package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.todo.service.SimpleTaskService;
import ru.job4j.todo.service.TaskService;

@AllArgsConstructor
@Controller
public class IndexController {

    private TaskService taskService;

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
       // model.addAttribute("tasks", taskService.findAll());
        return "redirect:/tasks";
    }

}