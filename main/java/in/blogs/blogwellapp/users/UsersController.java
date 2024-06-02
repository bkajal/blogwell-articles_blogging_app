package in.blogs.blogwellapp.users;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.blogs.blogwellapp.articles.ArticleEntity;
import in.blogs.blogwellapp.articles.ArticlesService;
import in.blogs.blogwellapp.security.JWTAuthentication;
import in.blogs.blogwellapp.security.JWTService;
import in.blogs.blogwellapp.users.dtos.CreateUserRequest;
import in.blogs.blogwellapp.users.dtos.UserResponse;
import in.blogs.blogwellapp.users.dtos.LoginUserRequest;
import in.blogs.blogwellapp.users.dtos.UpdateUserRequest;

@RestController
@RequestMapping("/users")
public class UsersController {
	private final UsersService usersService;
	private final ArticlesService articlesService;
	private final ModelMapper modelMapper;
	private final JWTService jwtService;
	
	public UsersController(UsersService usersService, ArticlesService articlesService, ModelMapper modelMapper, JWTService jwtService) {
		this.usersService = usersService;
		this.articlesService = articlesService;
		this.modelMapper = modelMapper;
		this.jwtService = jwtService;
	}

	private UserResponse userResponse;
	
	@GetMapping("")
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		List<UserEntity> userslist = usersService.getAllUsers();
		List<UserResponse> userResponselist = new ArrayList();
		JWTAuthentication jwtAuthentication = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
		String jwt = jwtAuthentication.getCredentials();
		
		for (UserEntity user : userslist) {
			userResponse = modelMapper.map(user, UserResponse.class);
			userResponse.setToken(jwt);
			userResponse.setStatusCode(HttpStatus.OK.value());
			userResponse.setMessage("success");
			userResponselist.add(userResponse);
		}
		
		
		return ResponseEntity.ok(userResponselist);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
		UserEntity user = usersService.getUser(id);
		JWTAuthentication jwtAuthentication = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
		String jwt = jwtAuthentication.getCredentials();
		
		userResponse = modelMapper.map(user, UserResponse.class);
		userResponse.setToken(jwt);
		userResponse.setStatusCode(HttpStatus.OK.value());
		userResponse.setMessage("success");
		
		return ResponseEntity.ok(userResponse);
	}
	
	@GetMapping("/user/{username}")
	public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
		UserEntity user = usersService.getUser(username);
		JWTAuthentication jwtAuthentication = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
		String jwt = jwtAuthentication.getCredentials();
		
		userResponse = modelMapper.map(user, UserResponse.class);
		userResponse.setToken(jwt);
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
		JWTAuthentication jwtAuthentication = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
		String jwt = jwtAuthentication.getCredentials();
		
		userResponse = modelMapper.map(savedUser, UserResponse.class);
		userResponse.setToken( jwt );
		userResponse.setStatusCode(HttpStatus.OK.value());
		userResponse.setMessage("login successfull");
		
		return ResponseEntity.ok(userResponse);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable long id) {
		UserEntity updateUser = usersService.updateUser(request, id);
		JWTAuthentication jwtAuthentication = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
		String jwt = jwtAuthentication.getCredentials();
		
		userResponse = modelMapper.map(updateUser, UserResponse.class);
		userResponse.setToken(jwt);
		userResponse.setStatusCode(HttpStatus.OK.value());
		userResponse.setMessage("update successfull");
		
		return ResponseEntity.ok(userResponse);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<UserResponse> deleteUserById(@PathVariable long id) {
		UserEntity foundUser = usersService.getUser(id);
		//delete mapping with articles
		List<ArticleEntity> articles = foundUser.getArticles();
		for (ArticleEntity article : articles) {
			articlesService.deleteArticleById(article.getId());
		}
		//delete user
		usersService.deleteUserById(foundUser.getId());
		
		userResponse = modelMapper.map(foundUser, UserResponse.class);
		userResponse.setToken(null);
		userResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
		userResponse.setMessage("DELETE success");
		
		return ResponseEntity.ok(userResponse);
	}
}
