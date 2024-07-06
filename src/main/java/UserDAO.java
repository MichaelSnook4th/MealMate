import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserDAO {
	
	private String URL = "jdbc:mysql://localhost:3306/mealmate";
	private String USER = "root";
	private String PASSWORD = "";
	
	private static final String INSERT_USERS_SQL = "INSERT INTO users (userId, firstName, lastName, address, email, password, isVerified, token) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
	
	public void registerUser(User user) throws SQLException {
		String token = UUID.randomUUID().toString();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)){
			preparedStatement.setString(1, user.getUserId().toString());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setString(4, user.getAddress());
			preparedStatement.setString(5, user.getEmail());
			preparedStatement.setString(6, user.getPassword());
			preparedStatement.setBoolean(7, false);
			preparedStatement.setString(8, token);
			preparedStatement.executeUpdate();
			EmailUtility.sendVerificationEmail(user.getEmail(), token);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public User checkLogin(String email, String password) throws SQLException {
		User user = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD)){
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				boolean isVerified = resultSet.getBoolean("isVerified");
				if (!isVerified) {
					throw new SQLException("Email not verified");
				}
				String userId = resultSet.getString("userId");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String address = resultSet.getString("address");
				user = new User(UUID.fromString(userId), firstName, lastName, address, email, password);
			} else {
				throw new SQLException("Invalid email or password");
			}
		}
		return user;
	}
	
	public void storeToken(UUID userId, String token) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USER_TOKEN)){
			statement.setString(1, token);
			statement.setString(2, userId.toString());
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
	
	public void storePasswordRecoveryToken(String userId, String token) throws SQLException{
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT_PASSWORD_RECOVERY_TOKEN)){
			if (tokenExistsForUser(userId)) {
				throw new SQLException("Duplicate token for user: " + userId);
			}
			statement.setString(1, userId);
			statement.setString(2, token);
			statement.executeUpdate();
		}
	}
	
	private boolean tokenExistsForUser(String userId) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM passwordRecovery WHERE userId = ?")){
			statement.setString(1, userId);
			try (ResultSet resultSet = statement.executeQuery()){
				return resultSet.next();
			}
		}
		
	}
	
	public boolean verifyPasswordRecoveryToken(String token) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(VERIFY_PASSWORD_RECOVERY_TOKEN)){
			statement.setString(1, token);
			try (ResultSet resultSet = statement.executeQuery()){
				return resultSet.next();
			}
		}
	}
	
	public boolean updatePasswordWithToken(String token, String newPassword) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_PASSWORD_BY_TOKEN)){
			statement.setString(1, newPassword);
			UUID userId = getUserIdByPasswordRecoveryToken(token);
			statement.setString(2, userId.toString());
			int rowsUpdated = statement.executeUpdate();
			return rowsUpdated > 0;
		}
	}
	
	public void deletePasswordRecoveryToken(String token) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_PASSWORD_RECOVERY_TOKEN)){
			statement.setString(1, token);
			statement.executeUpdate();
		}
	}
	public UUID getUserIdByPasswordRecoveryToken(String token) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(VERIFY_PASSWORD_RECOVERY_TOKEN)){
			statement.setString(1, token);
			try (ResultSet resultSet = statement.executeQuery()){
				if (resultSet.next()) {
					String userId = resultSet.getString("userId");
					return UUID.fromString(userId);
				}
				return null;
			}
		}
	}
	
	public User getUserByEmail(String email) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String userId = resultSet.getString("userId");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String address = resultSet.getString("address");
                    String password = resultSet.getString("password");

                    return new User(UUID.fromString(userId), firstName, lastName, address, email, password);
                }
                return null;
            }
        }
    }
	
	public void updateUser(User user) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SQL)){
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getAddress());
			statement.setString(4, user.getEmail());
			statement.setString(5, user.getPassword());
			statement.setString(6, user.getUserId().toString());
			statement.executeUpdate();
		}
	}
}
