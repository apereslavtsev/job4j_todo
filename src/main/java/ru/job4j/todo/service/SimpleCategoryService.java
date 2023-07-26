package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;
import java.util.List;

@AllArgsConstructor
@Service
public class SimpleCategoryService implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.getAll();
    }

    @Override
    public List<Category> getByIdList(List<Integer> id) {
        return categoryRepository.getByIdList(id);
    }
}
