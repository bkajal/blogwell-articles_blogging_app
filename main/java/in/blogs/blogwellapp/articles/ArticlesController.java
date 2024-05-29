package in.blogs.blogwellapp.articles;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.blogs.blogwellapp.articles.dtos.ArticleResponse;
import in.blogs.blogwellapp.articles.dtos.CreateArticleRequest;
import in.blogs.blogwellapp.articles.dtos.UpdateArticleRequest;
import in.blogs.blogwellapp.users.UserEntity;

@RestController
@RequestMapping("/articles")
public class ArticlesController {
	private final ArticlesService articlesService;
	private final ModelMapper modelMapper;

	public ArticlesController(ArticlesService articlesService, ModelMapper modelMapper) {
		this.articlesService = articlesService;
		this.modelMapper = modelMapper;
	}
	private ArticleResponse articleResponse;
	
	@GetMapping("")
	public ResponseEntity<List<ArticleResponse>> getAllArticle() {
		List<ArticleEntity> allArticles = articlesService.getAllArticles();
		List<ArticleResponse> articleResponses = new ArrayList();
		
		for (ArticleEntity article : allArticles) {
			articleResponse = modelMapper.map(article, ArticleResponse.class);
			articleResponse.setStatusCode(HttpStatus.OK.value());
			articleResponse.setMessage("success");
			articleResponses.add(articleResponse);
		}
		
		return ResponseEntity.ok(articleResponses);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ArticleResponse> getArticleById(@PathVariable long id) {
		ArticleEntity article = articlesService.getArticleById(id);
		
		articleResponse = modelMapper.map(article, ArticleResponse.class);
		articleResponse.setStatusCode(HttpStatus.OK.value());
		articleResponse.setMessage("success");
		
		return ResponseEntity.ok(articleResponse);
	}
	
	@GetMapping("/article/{slug}")
	public ResponseEntity<ArticleResponse> getArticleBySlug(@PathVariable String slug) {
		ArticleEntity article = articlesService.getArticleBySlug(slug);
		
		articleResponse = modelMapper.map(article, ArticleResponse.class);
		articleResponse.setStatusCode(HttpStatus.OK.value());
		articleResponse.setMessage("success");
		
		return ResponseEntity.ok(articleResponse);
	}
	
	@PostMapping("/article")
	public ResponseEntity<ArticleResponse> createArticle(@AuthenticationPrincipal UserEntity user, @RequestBody CreateArticleRequest req) {
		ArticleEntity savedArticle = articlesService.createArticle(req, user.getId());
		URI savedURI = URI.create("/aricles/"+ savedArticle.getSlug());
		
		articleResponse = modelMapper.map(savedArticle, ArticleResponse.class);
		articleResponse.setStatusCode(HttpStatus.OK.value());
		articleResponse.setMessage("success");
		
		return ResponseEntity.ok(articleResponse);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ArticleResponse> updateArticle(@RequestBody UpdateArticleRequest request, @PathVariable long id) {
		ArticleEntity updateArticle = articlesService.updateArticle(request, id);
		
		articleResponse = modelMapper.map(updateArticle, ArticleResponse.class);
		articleResponse.setStatusCode(HttpStatus.OK.value());
		articleResponse.setMessage("update successfull");
		
		return ResponseEntity.ok(articleResponse);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ArticleResponse> deleteUserById(@PathVariable long id) {
		ArticleEntity foundArticle = articlesService.getArticleById(id);
		articlesService.deleteArticleById(foundArticle.getId());
		
		articleResponse = modelMapper.map(foundArticle, ArticleResponse.class);
		articleResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
		articleResponse.setMessage("DELETE success");
		
		return ResponseEntity.ok(articleResponse);
	}
}
