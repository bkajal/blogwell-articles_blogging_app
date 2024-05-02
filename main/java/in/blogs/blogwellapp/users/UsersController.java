package in.blogs.blogwellapp.users;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.blogs.blogwellapp.errorresponse.dtos.ErrorResponse;
import in.blogs.blogwellapp.users.UsersService.InvalidCredentialsException;
import in.blogs.blogwellapp.users.UsersService.UserNotFoundException;
import in.blogs.blogwellapp.users.dtos.CreateUserRequest;
import in.blogs.blogwellapp.users.dtos.CreateUserResponse;
import in.blogs.blogwellapp.users.dtos.LoginUserRequest;

@RestController
@RequestMapping("/users")
public class UsersController {
	private final UsersService usersService;
	private final ModelMapper modelMapper;
	
	public UsersController(UsersService usersService, ModelMapper modelMapper) {
		this.usersService = usersService;
		this.modelMapper = modelMapper;
	}

	@PostMapping("/signup")
	public ResponseEntity<CreateUserResponse> signupUser(@RequestBody CreateUserRequest request) {
		UserEntity savedUser = usersService.createUser(request);
		URI savedURI = URI.create("/users/" + savedUser.getId());
		
		return ResponseEntity.created(savedURI)
				.body(modelMapper.map(savedUser, CreateUserResponse.class));
	}
	
	@PostMapping("/login")
	public ResponseEntity<CreateUserResponse> loginUser(@RequestBody LoginUserRequest request) {
		UserEntity savedUser = usersService.loginUser(request.getUsername(), request.getPassword());
		
		return ResponseEntity.ok(modelMapper.map(savedUser, CreateUserResponse.class));
	}
	
	@ExceptionHandler({
		UsersService.UserNotFoundException.class,
		UsersService.InvalidCredentialsException.class
		})
	ResponseEntity<ErrorResponse> handleUserNotFoundException(Exception exception){
		String message;
		HttpStatus status;
		
		if (exception instanceof UserNotFoundException) {
			message = exception.getMessage();
			status = HttpStatus.NOT_FOUND;
		}else if(exception instanceof InvalidCredentialsException){
			message = exception.getMessage();
			status = HttpStatus.UNAUTHORIZED;
		}else{
			message = "Something Went Wrong...";
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		ErrorResponse response = ErrorResponse.builder().message(message).build();
		
		return ResponseEntity.status(status).body(response);	
	}
}
