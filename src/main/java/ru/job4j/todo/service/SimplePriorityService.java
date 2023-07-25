package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimplePriorityService implements PriorityService {

    private PriorityRepository priorityRepository;

    @Override
    public List<Priority> getAll() {
        return priorityRepository.getAll();
    }

    @Override
    public Optional<Priority> getById(int id) {
        return priorityRepository.getById(id);
    }
}
