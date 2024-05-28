package in.blogs.blogwellapp.users;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import in.blogs.blogwellapp.exceptions.InvalidCredentialsException;
import in.blogs.blogwellapp.exceptions.UserNotFoundException;
import in.blogs.blogwellapp.users.dtos.CreateUserRequest;
import in.blogs.blogwellapp.users.dtos.UpdateUserRequest;

@Service
public class UsersService {
	private final UsersRepository usersRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;

	public UsersService(UsersRepository usersRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
		this.usersRepository = usersRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}

	public List<UserEntity> getAllUsers(){
		return usersRepository.findAll();
	}
	
	public UserEntity getUser(String username) {
		return usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
	}
	
	public UserEntity getUser(Long userId) {
		return usersRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
	}
	
	public UserEntity createUser(CreateUserRequest req) {
		UserEntity newUser = modelMapper.map(req, UserEntity.class);
		newUser.setPassword(passwordEncoder.encode(req.getPassword()));
		return usersRepository.save(newUser);
	}
	
	public UserEntity loginUser(String username, String password) {
		UserEntity user = getUser(username);
		boolean passMatch = passwordEncoder.matches(password, user.getPassword());
		if (!passMatch) throw new InvalidCredentialsException();
		return user;
	}
	
	public UserEntity updateUser(UpdateUserRequest request, long id) {
		UserEntity foundUser = getUser(id);
		
		if (request.getUsername() != null) {
			foundUser.setUsername(request.getUsername());
		}
		if (request.getEmail() != null) {
			foundUser.setEmail(request.getEmail());
		}
		if (request.getPassword() != null) {
			foundUser.setPassword(passwordEncoder.encode(request.getPassword()));
		}
		if (request.getBio() != null) {
			foundUser.setBio(request.getBio());
		}
		if (request.getImage() != null) {
			foundUser.setImage(request.getImage());
		}
		return usersRepository.save(foundUser);
	}
	
	public void deleteUserById(@PathVariable long id) {
		usersRepository.deleteById(id);
	}

}
