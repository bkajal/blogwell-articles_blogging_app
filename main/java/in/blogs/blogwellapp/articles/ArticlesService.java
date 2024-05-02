package in.blogs.blogwellapp.articles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.blogs.blogwellapp.articles.ArticleEntity.ArticleEntityBuilder;
import in.blogs.blogwellapp.articles.dtos.CreateArticleRequest;
import in.blogs.blogwellapp.articles.dtos.UpdateArticleRequest;
import in.blogs.blogwellapp.users.UserEntity;
import in.blogs.blogwellapp.users.UsersRepository;
import in.blogs.blogwellapp.users.UsersService;

@Service
public class ArticlesService {
	private ArticlesRepository articlesRepository;
	private UsersRepository usersRepository;
	
	public ArticlesService(ArticlesRepository articlesRepository, UsersRepository usersRepository) {
		this.articlesRepository = articlesRepository;
		this.usersRepository = usersRepository;
	}

	public List<ArticleEntity> getAllArticles() {
		return articlesRepository.findAll();
	}
	
	public ArticleEntity getArticleBySlug(String slug) {
		ArticleEntity article = articlesRepository.findBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug));
		if (article==null) {
			throw new ArticleNotFoundException(slug);
		}
		return article;
	}
	
	public ArticleEntity createArticle(CreateArticleRequest req, long authorId) {
		UserEntity author = usersRepository.findById(authorId).orElseThrow(() -> new UsersService.UserNotFoundException(authorId));
		
		ArticleEntity article = ArticleEntity.builder()
				.title(req.getTitle()).subtitle(req.getSubtitle())
				.body(req.getBody())
				.author(author)
				.build();
//				.setSlug(req.)  TODO : create slugification function
	
		return articlesRepository.save(article);
	}
	
	public ArticleEntity updateArticle(UpdateArticleRequest req, long articleId) {
		ArticleEntity article = articlesRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
		
		if (req.getTitle()!=null) {
			article.setTitle(req.getTitle());
//			article.setSlug(req.); TODO : set slug
		}
		if (article.getBody()!=null) {
			article.setBody(req.getBody());
		}
		if (article.getSubtitle()!=null) {
			article.setSubtitle(req.getSubtitle());
		}
		return articlesRepository.save(article);
	}
	
	static class ArticleNotFoundException extends IllegalArgumentException{
		public ArticleNotFoundException(String slug) {
			super("Article with : "+ slug + "not found.");
		}	
		
		public ArticleNotFoundException(Long articleId) {
			super("Article with Id : "+ articleId + "not found.");
		}	
	}
}
