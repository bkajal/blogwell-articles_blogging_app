package in.blogs.blogwellapp.users.dtos;

import lombok.Data;

@Data
public class UserResponse {
	private long id;
	private String username;
	private String email;
	private String bio;
	private String image;
	private String token;
}
