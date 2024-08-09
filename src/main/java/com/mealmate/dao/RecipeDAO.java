package com.mealmate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mealmate.DatabaseConnection;
import com.mealmate.model.Recipe;

public class RecipeDAO {
    private Connection connection;

    public RecipeDAO() {
        try {
            connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Recipe> getRecipesByCategory(String category) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            String query = "SELECT recipe_name FROM recipes WHERE category_id = (SELECT category_id FROM categories WHERE category_name = ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                recipes.add(new Recipe(rs.getString("recipe_name"), category));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public boolean insertRecipe(String recipeName, String category) {
        boolean rowInserted = false;
        try {
            String query = "INSERT INTO recipes (recipe_name, category_id) VALUES (?, (SELECT category_id FROM categories WHERE category_name = ?))";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, recipeName);
            ps.setString(2, category);
            rowInserted = ps.executeUpdate() > 0;
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowInserted;
    }

    public boolean deleteRecipe(String recipeName) {
        boolean rowDeleted = false;
        try {
            String query = "DELETE FROM recipes WHERE recipe_name = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, recipeName);
            rowDeleted = ps.executeUpdate() > 0;
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    public boolean updateRecipe(String oldRecipeName, String newRecipeName, String category) {
        boolean rowUpdated = false;
        try {
            String query = "UPDATE recipes SET recipe_name = ?, category_id = (SELECT category_id FROM categories WHERE category_name = ?) WHERE recipe_name = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, newRecipeName);
            ps.setString(2, category);
            ps.setString(3, oldRecipeName);
            rowUpdated = ps.executeUpdate() > 0;
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        try {
            String query = "SELECT r.recipe_name, c.category_name FROM recipes r JOIN categories c ON r.category_id = c.category_id";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                recipes.add(new Recipe(rs.getString("recipe_name"), rs.getString("category_name")));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        try {
            String query = "SELECT category_name FROM categories";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categories.add(rs.getString("category_name"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
