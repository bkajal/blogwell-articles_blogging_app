package in.blogs.blogwellapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import in.blogs.blogwellapp.users.UsersService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
	private final JWTAuthenticationFilter jwtAuthenticationFilter;
	private final JWTService jwtService;
	private final UsersService usersService;
	
    public AppSecurityConfig(@Lazy JWTAuthenticationFilter jwtAuthenticationFilter,@Lazy JWTService jwtService,@Lazy UsersService usersService) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.jwtService = jwtService;
		this.usersService = usersService;
	}

	@Bean
	JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception{
		return new JWTAuthenticationFilter(
				new JWTAuthenticationManager(jwtService, usersService)
				);
	}

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
    return http
    		.csrf(AbstractHttpConfigurer::disable)
    		.cors(CorsConfigurer::disable)
    		.authorizeHttpRequests(auth -> 
    		auth
    		    .requestMatchers("/**").permitAll()
    		    .requestMatchers("/swagger-ui/**").permitAll()
    		    .requestMatchers("/index.html").permitAll()
    			.requestMatchers("/users").permitAll()
    			.requestMatchers(HttpMethod.GET,"/users/*").permitAll()
    			.requestMatchers(HttpMethod.POST, "/users/signup","/users/login","/articles").permitAll()
    			.requestMatchers(HttpMethod.GET, "/articles", "/articles/*").permitAll()
    			.requestMatchers(HttpMethod.DELETE,"/users/*").permitAll()
    			.anyRequest().authenticated())
    			.addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class)
    		.build();
	}
}
