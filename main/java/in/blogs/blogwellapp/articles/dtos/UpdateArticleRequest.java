package in.blogs.blogwellapp.articles.dtos;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArticleRequest {
	@Nullable
	private String title;
	@Nullable
	private String body;
	@Nullable
	private String subtitle;

}
