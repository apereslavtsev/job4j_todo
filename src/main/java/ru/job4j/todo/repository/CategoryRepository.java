package ru.job4j.todo.repository;

import ru.job4j.todo.model.Category;

import java.util.List;

public interface CategoryRepository {

    public List<Category> getAll();

    public List<Category> getByIdList(List<Integer> id);

}
