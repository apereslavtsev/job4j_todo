package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernateCategoryStore implements CategoryRepository {

    private final CrudRepository crudRepository;

    @Override
    public List<Category> getAll() {
        return crudRepository.query(
                "from Category as c order by c.id", Category.class);
    }

    @Override
    public List<Category> getByIdList(List<Integer> id) {
        return crudRepository.query(
                "from Category as c where c.id in :fId", Category.class,
                Map.of("fId", id)
        );
    }
}
