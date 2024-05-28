package in.blogs.blogwellapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler({UserNotFoundException.class, InvalidCredentialsException.class})
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(Exception exception){
		HttpStatus status;
		int statusCode;
		String message;
		String error;
		
		if (exception instanceof UserNotFoundException) {
			status = HttpStatus.NOT_FOUND;
			statusCode = HttpStatus.NOT_FOUND.value();
			message = exception.getMessage();
			error = HttpStatus.NOT_FOUND.getReasonPhrase();
		} else if(exception instanceof InvalidCredentialsException) {
			status = HttpStatus.UNAUTHORIZED;
			statusCode = HttpStatus.UNAUTHORIZED.value();
			message = exception.getMessage();
			error = HttpStatus.UNAUTHORIZED.getReasonPhrase();
		} else {
			status = HttpStatus.BAD_REQUEST;
			statusCode = HttpStatus.BAD_REQUEST.value();
			message = " the server couldn't process the request due to a client error ";
			error = HttpStatus.BAD_REQUEST.getReasonPhrase();
		}
		
		ErrorResponse response = ErrorResponse.builder().statusCode(statusCode).message(message).error(error).build();
		
		return ResponseEntity.status(status).body(response);
	}
	
}
