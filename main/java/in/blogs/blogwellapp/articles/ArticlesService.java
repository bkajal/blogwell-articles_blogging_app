package in.blogs.blogwellapp.articles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import in.blogs.blogwellapp.articles.ArticleEntity.ArticleEntityBuilder;
import in.blogs.blogwellapp.articles.dtos.CreateArticleRequest;
import in.blogs.blogwellapp.articles.dtos.UpdateArticleRequest;
import in.blogs.blogwellapp.exceptions.ArticleNotFoundException;
import in.blogs.blogwellapp.exceptions.UserNotFoundException;
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
	
	public ArticleEntity getArticleById(long articleId) {
		ArticleEntity article = articlesRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
		if (article==null) {
			throw new ArticleNotFoundException(articleId);
		}
		return article;
	}
	
	public ArticleEntity getArticleBySlug(String slug) {
		ArticleEntity article = articlesRepository.findBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug));
		if (article==null) {
			throw new ArticleNotFoundException(slug);
		}
		return article;
	}
	
	public ArticleEntity createArticle(CreateArticleRequest req, long authorId) {
		UserEntity author = usersRepository.findById(authorId).orElseThrow(() -> new UserNotFoundException(authorId));
		
		String slug = req.getTitle().toLowerCase().replaceAll("\\s+", "-");
		ArticleEntity article = ArticleEntity.builder()
				.title(req.getTitle())
				.subtitle(req.getSubtitle())
				.slug(slug.replaceAll("[^a-z0-9\\\\-]", ""))
				.body(req.getBody())
				.author(author)
				.createdAt(LocalDate.now())
				.build();
	
		return articlesRepository.save(article);
	}
	
	public ArticleEntity updateArticle(UpdateArticleRequest req, long articleId) {
		ArticleEntity article = articlesRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
		String slug = req.getTitle().toLowerCase().replaceAll("\\s+", "-");
		
		if (req.getTitle()!=null) {
			article.setTitle(req.getTitle());
			article.setSlug(slug.replaceAll("[^a-z0-9\\\\-]", ""));
		}
		if (article.getBody()!=null) {
			article.setBody(req.getBody());
		}
		if (article.getSubtitle()!=null) {
			article.setSubtitle(req.getSubtitle());
		}
		return articlesRepository.save(article);
	}
	
	public void deleteArticleById(@PathVariable long id) {
		articlesRepository.deleteById(id);
	}
}
