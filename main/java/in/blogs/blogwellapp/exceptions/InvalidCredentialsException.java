package in.blogs.blogwellapp.exceptions;

public class InvalidCredentialsException extends RuntimeException{
	public InvalidCredentialsException() {
		super("Invalid Username and Password Combination");
	}
	
}
