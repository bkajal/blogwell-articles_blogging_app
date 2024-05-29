package in.blogs.blogwellapp.articles.dtos;

import in.blogs.blogwellapp.users.dtos.UserResponse;
import lombok.Data;

@Data
public class ArticleResponse {
	private int statusCode;
	private String message;
	private long id;
	private String title;
	private String subtitle;
	private String slug;
	private String body;
	private String createdAt;
	private UserResponse author;
}
