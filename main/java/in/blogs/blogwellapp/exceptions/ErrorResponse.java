
package in.blogs.blogwellapp.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
	private int statusCode;
	private String message;
	private String error;
}
