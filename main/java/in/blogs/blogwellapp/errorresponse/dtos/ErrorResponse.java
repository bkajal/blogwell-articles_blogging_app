
package in.blogs.blogwellapp.errorresponse.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
	private String message;
}
