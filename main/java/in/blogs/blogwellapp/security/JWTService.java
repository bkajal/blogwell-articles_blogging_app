package in.blogs.blogwellapp.security;


import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JWTService {
	// move the key to a separate .properties file not in Git
//	@Value("${app.jwt-secret}")
	private static String JWT_KEY = "08d2e451deb2945535d3a532765ae5214477ffba583212f098ea7ed56fc73137";
	private Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);
	
	public String createJWT(Long userId) {
		
		return JWT.create()
			.withSubject(userId.toString())
			.withIssuedAt(new Date())
			.withExpiresAt(new Date(System.currentTimeMillis()+5000L))
			.sign(algorithm);
	}

	public Long retrieveUserId(String JWTString) {
			var decodedJWT = JWT.decode(JWTString);
			String userId = decodedJWT.getSubject();
			return Long.valueOf(userId);	
	}
}
