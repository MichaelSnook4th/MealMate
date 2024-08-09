package com.mealmate.service;

import com.mealmate.dao.UserDAO;
import com.mealmate.model.User;
import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public boolean deleteUserByEmail(String email) {
        return userDAO.deleteUserByEmail(email);
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean insertUser(User user) {
        return userDAO.insertUser(user);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
