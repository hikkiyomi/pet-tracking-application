package ru.hikkiyomi.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.hikkiyomi.dao.UserDao;
import ru.hikkiyomi.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements CommonCrudService<User> {
    @Autowired
    private UserDao userDao;

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public void save(User obj) {
        if (userDao.existsByUsername(obj.getUsername())) {
            throw new EntityExistsException("Username should be unique.");
        }

        userDao.save(obj);
    }

    @Override
    public void delete(User obj) {
        if (!userDao.existsByUsername(obj.getUsername())) {
            throw new EntityNotFoundException("No such user exists.");
        }

        userDao.delete(obj);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    public User getByUsername(String username) {
        return userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));
    }

    public User getPrincipal() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return getByUsername(username);
    }
}
