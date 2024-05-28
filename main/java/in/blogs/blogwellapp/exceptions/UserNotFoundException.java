package in.blogs.blogwellapp.exceptions;

public class UserNotFoundException extends RuntimeException{
	
	public UserNotFoundException(String username) {
		super("User with username : "+ username + " not found");
	}
	public UserNotFoundException(Long userId) {
		super("User with userid : "+ userId + " not found");
	}
}
