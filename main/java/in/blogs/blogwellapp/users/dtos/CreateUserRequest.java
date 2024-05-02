package in.blogs.blogwellapp.users.dtos;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
	@NonNull
	private String username;
	@NonNull
	private String password;
	@NonNull
	private String email;

}
