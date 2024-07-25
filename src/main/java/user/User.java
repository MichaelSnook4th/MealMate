package user;
import java.util.UUID;

public class User {

    // User variables
    private UUID userId;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String password;

    public User(UUID userId, String firstName, String lastName, String address, String email, String password){
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    public UUID getUserId(){
        return userId;
    }
    public void setUserId(UUID userId){
        this.userId = userId;
    }

    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
