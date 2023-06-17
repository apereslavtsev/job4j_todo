package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {
    
    private static final Logger LOG = LoggerFactory.getLogger(SimpleUserService.class.getName());
    
    private UserRepository userRepository;

    @Override
    public Optional<User> save(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception exception) {  
            LOG.error("Exception in save user", exception);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLoginAndPassword(String email, String password) {
        return userRepository.findByLoginAndPassword(email, password);
    }

}
