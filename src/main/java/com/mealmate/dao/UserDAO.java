package com.mealmate.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mealmate.beans.User;

public class UserDAO {

    private String URL = "jdbc:mysql://localhost:3306/mealmate";
    private String USER = "root";
    private String PASSWORD = "";

    private static final String INSERT_USERS_SQL = "INSERT INTO users (firstName, lastName, address, email, password, isVerified, token) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final String SELECT_USER_BY_TOKEN = "SELECT * FROM users WHERE token = ?";
    private static final String UPDATE_USER_VERIFICATION = "UPDATE users SET isVerified = true WHERE token = ?";
    private static final String UPDATE_USER_TOKEN = "UPDATE users SET token = ? WHERE userId = ?";
    private static final String INSERT_PASSWORD_RECOVERY_TOKEN = "INSERT INTO passwordRecovery (userId, token) VALUES (?, ?)";
    private static final String VERIFY_PASSWORD_RECOVERY_TOKEN = "SELECT userId FROM passwordRecovery WHERE token = ?";
    private static final String UPDATE_PASSWORD_BY_TOKEN = "UPDATE users SET password = ? WHERE userId = ?";
    private static final String GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?"; 
    private static final String DELETE_PASSWORD_RECOVERY_TOKEN = "DELETE FROM passwordRecovery WHERE token = ?";
    private static final String UPDATE_USER_SQL = "UPDATE users SET firstName = ?, lastName = ?, address = ?, email = ?, password = ? WHERE userId = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE userId = ?";
    private static final String INSERT_USER_CATEGORIES = "INSERT INTO user_categories (userId, category_id) VALUES (?, ?)";
    private static final String DELETE_USER_CATEGORIES = "DELETE FROM user_categories WHERE userId = ?";
    private static final String SELECT_USER_CATEGORIES = "SELECT c.category_name FROM user_categories uc JOIN categories c ON uc.category_id = c.category_id WHERE uc.userId = ?";
    
    protected Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void registerUser(User user, String[] categories) throws SQLException {
        try (Connection connection = getConnection()) {
            String token = UUID.randomUUID().toString();
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getAddress());
                preparedStatement.setString(4, user.getEmail());
                preparedStatement.setString(5, user.getPassword());
                preparedStatement.setBoolean(6, false); // Assuming user is not verified at registration
                preparedStatement.setString(7, token);
                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getInt(1));
                    }
                }
            }

            if (categories != null) {
                for (String categoryId : categories) {
                    try (PreparedStatement insertCategoryStatement = connection.prepareStatement(INSERT_USER_CATEGORIES)) {
                        insertCategoryStatement.setInt(1, user.getUserId());
                        insertCategoryStatement.setInt(2, Integer.parseInt(categoryId));
                        insertCategoryStatement.executeUpdate();
                    }
                }
            }
        }
    }



    public User checkLogin(String email, String password) throws SQLException {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                boolean isVerified = resultSet.getBoolean("isVerified");
                if (!isVerified) {
                    throw new SQLException("Email not verified");
                }
                int userId = resultSet.getInt("userId");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                user = new User(userId, firstName, lastName, address, email, password);
            } else {
                throw new SQLException("Invalid email or password");
            }
        }
        return user;
    }

    public void storeToken(int userId, String token) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_TOKEN)) {
            statement.setString(1, token);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }

    public boolean verifyUser(String token) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_VERIFICATION)) {
            preparedStatement.setString(1, token);
            int updated = preparedStatement.executeUpdate();
            return updated > 0;
        }
    }

    public void storePasswordRecoveryToken(int userId, String token) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PASSWORD_RECOVERY_TOKEN)) {
            if (tokenExistsForUser(userId)) {
                throw new SQLException("Duplicate token for user: " + userId);
            }
            statement.setInt(1, userId);
            statement.setString(2, token);
            statement.executeUpdate();
        }
    }

    private boolean tokenExistsForUser(int userId) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM passwordRecovery WHERE userId = ?")) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public boolean verifyPasswordRecoveryToken(String token) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(VERIFY_PASSWORD_RECOVERY_TOKEN)) {
            statement.setString(1, token);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public boolean updatePasswordWithToken(String token, String newPassword) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PASSWORD_BY_TOKEN)) {
            statement.setString(1, newPassword);
            int userId = getUserIdByPasswordRecoveryToken(token);
            statement.setInt(2, userId);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public void deletePasswordRecoveryToken(String token) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PASSWORD_RECOVERY_TOKEN)) {
            statement.setString(1, token);
            statement.executeUpdate();
        }
    }

    public int getUserIdByPasswordRecoveryToken(String token) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(VERIFY_PASSWORD_RECOVERY_TOKEN)) {
            statement.setString(1, token);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("userId");
                }
                return -1;
            }
        }
    }

    public User getUserByEmail(String email) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("userId");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String address = resultSet.getString("address");
                    String password = resultSet.getString("password");

                    return new User(userId, firstName, lastName, address, email, password);
                }
                return null;
            }
        }
    }

    public void updateUser(User user) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SQL)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getAddress());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setInt(6, user.getUserId());
            statement.executeUpdate();
        }
    }

    public void deleteUser(User user) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setInt(1, user.getUserId());
            statement.execute();
        }
    }
    
    public List<String> getUserCategories(int userId) throws SQLException {
        List<String> categories = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_CATEGORIES)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String categoryName = resultSet.getString("category_name");
                    categories.add(categoryName);
                }
            }
        }
        return categories;
    }
}


