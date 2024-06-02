package in.blogs.blogwellapp.exceptions;

public class CommentNotFoundException extends RuntimeException{
	public CommentNotFoundException(String slug) {
		super("Comment from article : "+ slug + " not found.");
	}	
	
	public CommentNotFoundException(Long id) {
		super("Comment for Id : "+ id + " not found.");
	}	
}
