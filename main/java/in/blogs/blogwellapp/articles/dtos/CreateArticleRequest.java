package in.blogs.blogwellapp.articles.dtos;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleRequest {
	@NonNull
	private String title;
	@NonNull
	private String body;
	@Nullable
	private String subtitle;

}
