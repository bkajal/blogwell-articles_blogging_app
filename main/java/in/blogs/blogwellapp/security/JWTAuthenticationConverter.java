package in.blogs.blogwellapp.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

import jakarta.servlet.http.HttpServletRequest;

public class JWTAuthenticationConverter implements AuthenticationConverter{

	@Override
	public Authentication convert(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader==null || !authHeader.startsWith("Bearer ")) {
			return null;
		} 
		
		String jwt = authHeader.substring(7);
		
		return new JWTAuthentication(jwt);
	}

}
