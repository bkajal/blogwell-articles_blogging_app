package in.blogs.blogwellapp.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
	    @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			
	    return http
	    		.csrf(AbstractHttpConfigurer::disable)
	    		.cors(CorsConfigurer::disable)
	    		.authorizeHttpRequests(auth -> 
	    			auth.requestMatchers(HttpMethod.POST, "/users/signup","/users/login").permitAll()
				.anyRequest().authenticated())
	    		.build();
		}
}
