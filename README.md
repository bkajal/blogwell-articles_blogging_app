# Blogwell-articles_blogging_app
 Article Blogging Application
Developed a backend API for an articles blogging application using Spring technologies.
Implemented functionalities for users to write articles and comment on them.
Created controllers, DTOs, entities, services, and repositories for data management.
Implemented robust exception handling for API calls.
Secured the application using Spring Security with JWT for user authentication.
Utilized java-jwt library with HMAC256 algorithm for secure token generation.
Implemented a SecurityFilterChain using HttpSecurity for request authorization.
Configured JWTAuthenticationFilter for user authentication before AnonymousAuthenticationFilter.
Defined URL mappings for specific access control.
Employed AuthenticationConverter and AuthenticationManager with Authorization headers to validate and authenticate users.
Set the authenticated user context upon successful JWT authentication.
