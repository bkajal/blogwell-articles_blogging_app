package in.blogs.blogwellapp.comments.dtos;

import in.blogs.blogwellapp.articles.dtos.ArticleResponse;
import in.blogs.blogwellapp.users.dtos.UserResponse;
import lombok.Data;

@Data
public class CommentResponse {
	private int statusCode;
	private String message;
	private String comment;
	private String createdAt;
	private UserResponse author;
	private ArticleResponse article;
}
