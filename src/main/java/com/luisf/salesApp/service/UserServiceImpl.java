package com.luisf.salesApp.service;

import com.luisf.salesApp.model.Order;
import com.luisf.salesApp.model.User;
import com.luisf.salesApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found")));
    }

    @Override
    public Optional<User> update(Long id, User newUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

        user.setName(newUser.getName());
        user.setLastname(newUser.getLastname());
        user.setPassword(newUser.getPassword());
        user.setRole(newUser.getRole());
        user.setEmail(newUser.getEmail());

        return Optional.of(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        userRepository.delete(user);
    }

    @Override
    public List<Order> getAllOrders(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        return user.getOrders();
    }

    @Override
    public Optional<Order> getOrderById(Long userId, Long orderId) {
        return Optional.ofNullable(userRepository.getOrderById(userId, orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found")));
    }
}
