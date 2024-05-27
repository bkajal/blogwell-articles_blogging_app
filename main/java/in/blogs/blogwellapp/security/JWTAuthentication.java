package in.blogs.blogwellapp.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import in.blogs.blogwellapp.users.UserEntity;

public class JWTAuthentication implements Authentication{
	String jwt;
	UserEntity userEntity;
	
	public JWTAuthentication(String jwt) {
		this.jwt = jwt;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the credentials of the {@Code Authentication} request
	 * For eg : the password, or the Bearer token, or the Cookie.
	 * @return
	 */
	@Override
	public String getCredentials() {
		// TODO Auto-generated method stub
		return jwt;
	}

	@Override
	public Object getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the Principal of the {@Code Authentication} request
	 * The 'Principal' is the entity that is being authenticated
	 * In this case it is the user
	 * @return
	 */
	@Override
	public UserEntity getPrincipal() {
		// TODO Auto-generated method stub
		return userEntity;
	}
	
	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return (userEntity!=null);
	}

}
