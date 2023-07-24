package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    public List<Category> getAll();

    public Optional<Category> getById(int id);

}
