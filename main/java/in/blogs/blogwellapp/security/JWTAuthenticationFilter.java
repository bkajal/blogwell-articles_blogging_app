package in.blogs.blogwellapp.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class JWTAuthenticationFilter extends AuthenticationFilter{

	//dependency injected of JWTAuthenticationManager
	private final JWTAuthenticationManager jwtAuthenticationManager;
	
	public JWTAuthenticationFilter( JWTAuthenticationManager jwtAuthenticationManager) {
		super(jwtAuthenticationManager, new JWTAuthenticationConverter());
		this.jwtAuthenticationManager = jwtAuthenticationManager;
		
		//set jwtauthentication to securityconntext which is accessible across application
		this.setSuccessHandler((request, response, authentication) -> {
			SecurityContextHolder.getContext().setAuthentication(authentication);
		});
	}

}
