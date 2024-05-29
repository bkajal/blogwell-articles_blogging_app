package in.blogs.blogwellapp.exceptions;

public class ArticleNotFoundException extends RuntimeException{
	
	public ArticleNotFoundException(String slug) {
		super("Article with : "+ slug + " not found.");
	}	
	
	public ArticleNotFoundException(Long articleId) {
		super("Article with Id : "+ articleId + " not found.");
	}	
}
