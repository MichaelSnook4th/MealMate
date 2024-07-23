import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RecipeDAO {
	private String URL = "jdbc:mysql://localhost:3306/mealmate";
	private String USER = "root";
	private String PASSWORD = "";
	
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
	
	public Recipe getRecipeName(String recipeName) {
		if (recipeName == null || recipeName.isEmpty()) {
			return null;
		}
		RecipeFactory recipeFactory = new RecipeFactory();
		switch (recipeName) {
			case "Pizza":
				return recipeFactory.createPizza();
			default:
				return null;
		}
	}
}
