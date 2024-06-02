package in.blogs.blogwellapp.comments;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import in.blogs.blogwellapp.articles.ArticleEntity;
import in.blogs.blogwellapp.articles.ArticlesService;
import in.blogs.blogwellapp.comments.dtos.CreateCommentRequest;
import in.blogs.blogwellapp.exceptions.CommentNotFoundException;
import in.blogs.blogwellapp.users.UserEntity;

@Service
public class CommentsService {
	
	private final ModelMapper modelMapper;
	private final CommentsRepository commentsRepository;
	private final ArticlesService articlesService;
	
	public CommentsService(CommentsRepository commentsRepository, ArticlesService articlesService, ModelMapper modelMapper) {
		this.commentsRepository = commentsRepository;
		this.articlesService = articlesService;
		this.modelMapper = modelMapper;
	}

	public List<CommentEntity> getAllComments(long articleId) {
		ArticleEntity article = articlesService.getArticleById(articleId);
		
		return article.getComments();
	}
	
	public CommentEntity getCommentByArticleAndCommentId(long commentId, ArticleEntity article) {
		return commentsRepository.findByIdAndArticle(commentId, article).orElseThrow(() -> new CommentNotFoundException(commentId));
	}
	
	public CommentEntity createComment(CreateCommentRequest req, long id) {
		ArticleEntity article = articlesService.getArticleById(id);
		UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	/*	CommentEntity comment = CommentEntity.builder()
			.comment(req.getComment())
			.createdAt(LocalDate.now())
			.article(article)
			.author(user); //Set Author as login user, not as article author
			.build();
	*/
		CommentEntity comment = modelMapper.map(req, CommentEntity.class);
		if(req.getComment() == null) {
			throw new CommentNotFoundException(id);
		}
		comment.setComment(req.getComment());
		comment.setCreatedAt(LocalDate.now());
		comment.setArticle(article);
		comment.setAuthor(user);
		
		return commentsRepository.save(comment);
	}
	
	public void deleteComment(long commentId) {
		commentsRepository.deleteById(commentId);
	}
}
