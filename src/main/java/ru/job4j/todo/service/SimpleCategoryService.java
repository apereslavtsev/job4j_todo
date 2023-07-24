package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimpleCategoryService implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.getAll();
    }

    @Override
    public Optional<Category> getById(int id) {
        return categoryRepository.getById(id);
    }
}
