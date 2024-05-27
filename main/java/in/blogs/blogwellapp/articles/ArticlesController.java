package in.blogs.blogwellapp.articles;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.blogs.blogwellapp.users.UserEntity;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

	@GetMapping("")
	public String article() {
		return "get all Articles";
	}
	
	@GetMapping("/{id}")
	public String getArticleById(@PathVariable String id) {
		return "get Article with Id : "+id;
	}
	
	@PostMapping("")
	public String createArticle(@AuthenticationPrincipal UserEntity user) {
		return "create Article called by "+user.getUsername();
	}
}
