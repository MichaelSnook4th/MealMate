package com.mealmate.service;

import com.mealmate.dao.UserDAO;
import com.mealmate.beans.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User getUserByEmail(String email) throws SQLException {
        return userDAO.getUserByEmail(email);
    }

    public boolean deleteUserByEmail(String email) {
        try {
            User user = userDAO.getUserByEmail(email);
            if (user != null) {
                userDAO.deleteUser(user);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        try {
            userDAO.updateUser(user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertUser(User user, String[] categories) {
        try {
            userDAO.registerUser(user, categories);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
