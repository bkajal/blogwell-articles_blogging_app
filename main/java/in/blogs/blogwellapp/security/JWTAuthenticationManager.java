package in.blogs.blogwellapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import in.blogs.blogwellapp.users.UserEntity;
import in.blogs.blogwellapp.users.UsersService;

public class JWTAuthenticationManager implements AuthenticationManager{
	private final JWTService jwtService;
	private final UsersService usersService;

	public JWTAuthenticationManager(JWTService jwtService, UsersService usersService) {
		this.jwtService = jwtService;
		this.usersService = usersService;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		if (authentication instanceof JWTAuthentication) {
			JWTAuthentication jwtAuthentication = (JWTAuthentication) authentication;
			String jwt = jwtAuthentication.getCredentials();
			Long userId = jwtService.retrieveUserId(jwt);
			UserEntity userEntity = usersService.getUser(userId);
			
			jwtAuthentication.userEntity = userEntity;
			jwtAuthentication.setAuthenticated(true);
			
			return jwtAuthentication;
		}
		
		throw new IllegalAccessError("Cannot authenticate with non-JWT authentication");
	}

}
