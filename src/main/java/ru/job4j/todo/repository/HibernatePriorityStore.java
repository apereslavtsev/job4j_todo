package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernatePriorityStore implements PriorityRepository {

    private final CrudRepository crudRepository;

    @Override
    public List<Priority> getAll() {
        return crudRepository.query(
                "from Priority as p order by p.id", Priority.class);
    }

    @Override
    public Optional<Priority> getById(int id) {

        return crudRepository.optional(
                "from Priority as p where p.id = :fId", Priority.class,
                Map.of("fId", id)
        );
    }
}
