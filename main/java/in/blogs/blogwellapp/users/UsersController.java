package in.blogs.blogwellapp.users;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.blogs.blogwellapp.security.JWTService;
import in.blogs.blogwellapp.users.dtos.CreateUserRequest;
import in.blogs.blogwellapp.users.dtos.UserResponse;
import in.blogs.blogwellapp.users.dtos.LoginUserRequest;
import in.blogs.blogwellapp.users.dtos.UpdateUserRequest;

@RestController
@RequestMapping("/users")
public class UsersController {
	private final UsersService usersService;
	private final ModelMapper modelMapper;
	private final JWTService jwtService;
	
	public UsersController(UsersService usersService, ModelMapper modelMapper, JWTService jwtService) {
		this.usersService = usersService;
		this.modelMapper = modelMapper;
		this.jwtService = jwtService;
	}
	
	private UserResponse userResponse;
	
	@GetMapping("")
	public ResponseEntity<ArrayList<UserResponse>> getAllUsers() {
		List<UserEntity> userslist = usersService.getAllUsers();
		ArrayList<UserResponse> userResponselist = new ArrayList();
		
		for (UserEntity user : userslist) {
			userResponse = modelMapper.map(user, UserResponse.class);
			userResponse.setToken(null);
			userResponse.setStatusCode(HttpStatus.OK.value());
			userResponse.setMessage("success");
			userResponselist.add(userResponse);
		}
		
		
		return ResponseEntity.ok(userResponselist);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
		UserEntity user = usersService.getUser(id);
		
		userResponse = modelMapper.map(user, UserResponse.class);
		userResponse.setToken(null);
		userResponse.setStatusCode(HttpStatus.OK.value());
		userResponse.setMessage("success");
		
		return ResponseEntity.ok(userResponse);
	}

	@PostMapping("/signup")
	public ResponseEntity<UserResponse> signupUser(@RequestBody CreateUserRequest request) {
		UserEntity savedUser = usersService.createUser(request);
		URI savedURI = URI.create("/users/" + savedUser.getId());
		
		userResponse = modelMapper.map(savedUser, UserResponse.class);
		userResponse.setToken( jwtService.createJWT(savedUser.getId()) );
		userResponse.setStatusCode(HttpStatus.CREATED.value());
		userResponse.setMessage("signup successfull");
		
		return ResponseEntity.created(savedURI)
				.body(userResponse);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest request) {
		UserEntity savedUser = usersService.loginUser(request.getUsername(), request.getPassword());
		
		userResponse = modelMapper.map(savedUser, UserResponse.class);
		userResponse.setToken( jwtService.createJWT(savedUser.getId()) );
		userResponse.setStatusCode(HttpStatus.OK.value());
		userResponse.setMessage("login successfull");
		
		return ResponseEntity.ok(userResponse);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable long id) {
		UserEntity updateUser = usersService.updateUser(request, id);
		
		userResponse = modelMapper.map(updateUser, UserResponse.class);
		userResponse.setToken(null);
		userResponse.setStatusCode(HttpStatus.OK.value());
		userResponse.setMessage("update successfull");
		
		return ResponseEntity.ok(userResponse);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<UserResponse> deleteUserById(@PathVariable long id) {
		UserEntity foundUser = usersService.getUser(id);
		usersService.deleteUserById(foundUser.getId());
		
		userResponse = modelMapper.map(foundUser, UserResponse.class);
		userResponse.setToken(null);
		userResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
		userResponse.setMessage("DELETE success");
		
		return ResponseEntity.ok(userResponse);
	}
	
	
}
