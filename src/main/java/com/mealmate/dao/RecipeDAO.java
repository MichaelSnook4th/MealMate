package com.mealmate.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mealmate.beans.Recipe;

public class RecipeDAO {

    private String URL = "jdbc:mysql://localhost:3306/mealmate";
    private String USER = "root";
    private String PASSWORD = "";

    private static RecipeDAO instance;
    
    private static final String SELECT_RECIPES_BY_CATEGORY = "SELECT recipe_name FROM recipes WHERE category_id = (SELECT category_id FROM categories WHERE category_name = ?)";
    private static final String INSERT_RECIPE_SQL = "INSERT INTO recipes (recipe_name, category_id) VALUES (?, (SELECT category_id FROM categories WHERE category_name = ?))";
    private static final String DELETE_RECIPE_SQL = "DELETE FROM recipes WHERE recipe_name = ?";
    private static final String UPDATE_RECIPE_SQL = "UPDATE recipes SET recipe_name = ?, category_id = (SELECT category_id FROM categories WHERE category_name = ?) WHERE recipe_name = ?";
    private static final String SELECT_ALL_RECIPES = "SELECT recipe_name, category_name FROM recipes INNER JOIN categories ON recipes.category_id = categories.category_id";
    private static final String SELECT_ALL_CATEGORIES = "SELECT category_name FROM categories";
    private static final String SELECT_INGREDIENTS_BY_RECIPE_NAME = "SELECT ingredient_name FROM ingredients WHERE recipe_id = (SELECT recipe_id FROM recipes WHERE recipe_name = ?)";

    private RecipeDAO() {
    }
    
    public static synchronized RecipeDAO getInstance() {
    	if (instance == null) {
    		instance = new RecipeDAO();
    	}
    	return instance;
    }
    
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

    public List<Recipe> getRecipesByCategory(String category) {
        List<Recipe> recipes = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RECIPES_BY_CATEGORY)) {
            preparedStatement.setString(1, category);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    recipes.add(new Recipe(resultSet.getString("recipe_name"), category));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public boolean insertRecipe(String recipeName, String category) {
        boolean rowInserted = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RECIPE_SQL)) {
            preparedStatement.setString(1, recipeName);
            preparedStatement.setString(2, category);
            rowInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowInserted;
    }

    public boolean deleteRecipe(String recipeName) {
        boolean rowDeleted = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RECIPE_SQL)) {
            preparedStatement.setString(1, recipeName);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    public boolean updateRecipe(String oldRecipeName, String newRecipeName, String category) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RECIPE_SQL)) {
            preparedStatement.setString(1, newRecipeName);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, oldRecipeName);
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RECIPES);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String recipeName = resultSet.getString("recipe_name");
                String categoryName = resultSet.getString("category_name");

                System.out.println("Retrieved Recipe: " + recipeName + " in category: " + categoryName);

                recipes.add(new Recipe(recipeName, categoryName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATEGORIES);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                categories.add(resultSet.getString("category_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<String> getIngredientsByRecipeName(String recipeName) {
        List<String> ingredients = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INGREDIENTS_BY_RECIPE_NAME)) {
            preparedStatement.setString(1, recipeName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ingredients.add(resultSet.getString("ingredient_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }
    
    public List<Recipe> getRecipesByCategoryIds(List<Integer> categoryIds) throws SQLException {
        List<Recipe> recipes = new ArrayList<>();
        if (categoryIds.isEmpty()) {
            return recipes;
        }

        String placeholders = categoryIds.stream().map(id -> "?").collect(Collectors.joining(","));
        String sql = "SELECT recipe_name, category_name FROM recipes r " +
                     "JOIN categories c ON r.category_id = c.category_id " +
                     "WHERE r.category_id IN (" + placeholders + ")";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (int i = 0; i < categoryIds.size(); i++) {
                preparedStatement.setInt(i + 1, categoryIds.get(i));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String recipeName = resultSet.getString("recipe_name");
                    String categoryName = resultSet.getString("category_name");
                    recipes.add(new Recipe(recipeName, categoryName));
                }
            }
        }
        return recipes;
    }


    public List<Integer> getCategoryIdsByName(List<String> categoryNames) throws SQLException {
        List<Integer> categoryIds = new ArrayList<>();
        if (categoryNames.isEmpty()) {
            return categoryIds; 
        }
        String placeholders = categoryNames.stream().map(name -> "?").collect(Collectors.joining(","));
        String sql = "SELECT category_id FROM categories WHERE category_name IN (" + placeholders + ")";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            for (int i = 0; i < categoryNames.size(); i++) {
                preparedStatement.setString(i + 1, categoryNames.get(i));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    categoryIds.add(resultSet.getInt("category_id"));
                }
            }
        }
        return categoryIds;
    }

}
