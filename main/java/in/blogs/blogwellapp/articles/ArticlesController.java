package in.blogs.blogwellapp.articles;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

	@GetMapping("")
	public String article() {
		return "Articles";
	}
}
