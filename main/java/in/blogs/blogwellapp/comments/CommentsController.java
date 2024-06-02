package in.blogs.blogwellapp.comments;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.blogs.blogwellapp.articles.ArticleEntity;
import in.blogs.blogwellapp.articles.ArticlesService;
import in.blogs.blogwellapp.comments.dtos.CommentResponse;
import in.blogs.blogwellapp.comments.dtos.CreateCommentRequest;
import in.blogs.blogwellapp.exceptions.ArticleNotFoundException;
import in.blogs.blogwellapp.users.UserEntity;

@RestController
@RequestMapping("/articles")
public class CommentsController {
	
	private CommentsService commentService;
	private ArticlesService articlesService;
	private ModelMapper modelMapper;
	
	public CommentsController(CommentsService commentService, ArticlesService articlesService, ModelMapper modelMapper) {
		this.commentService = commentService;
		this.articlesService = articlesService;
		this.modelMapper = modelMapper;
	}

	private CommentResponse commentResponse;

	@GetMapping("/{slug}/comments")
	public ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable String slug) {
		ArticleEntity article = articlesService.getArticleBySlug(slug);
		
		List<CommentEntity> allComments = commentService.getAllComments(article.getId());
		List<CommentResponse> commentResponses = new ArrayList<CommentResponse>();
		
		for (CommentEntity comment : allComments) {
			commentResponse = modelMapper.map(comment, CommentResponse.class);
			commentResponse.setStatusCode(HttpStatus.OK.value());
			commentResponse.setMessage("success");
			commentResponses.add(commentResponse);
		}
		
		return ResponseEntity.ok(commentResponses);
	}

	@GetMapping("/{slug}/comments/{id}")
	public ResponseEntity<CommentResponse> getCommentByCommentId(@PathVariable String slug , @PathVariable long id) {
		 ArticleEntity articleBySlug = articlesService.getArticleBySlug(slug);
		 CommentEntity comment = commentService.getCommentByArticleAndCommentId(id, articleBySlug);

		 commentResponse = modelMapper.map(comment, CommentResponse.class);
		 commentResponse.setStatusCode(HttpStatus.OK.value());
		 commentResponse.setMessage("success");		
		 return ResponseEntity.ok(commentResponse);
	}
	
	@GetMapping("article/{id}/comments")
	public ResponseEntity<List<CommentResponse>> getCommentByArticleId(@PathVariable long id) {
		ArticleEntity article = articlesService.getArticleById(id);
		
		List<CommentEntity> comments = article.getComments();
		List<CommentResponse> commentResponses = new ArrayList<CommentResponse>();
		
		for (CommentEntity comment : comments) {
			commentResponse = modelMapper.map(comment, CommentResponse.class);
			commentResponse.setStatusCode(HttpStatus.OK.value());
			commentResponse.setMessage("success");
			commentResponses.add(commentResponse);
		}
		
		return ResponseEntity.ok(commentResponses);
	}
	
	@PostMapping("/{id}/comments/comment")
	public ResponseEntity<CommentResponse> createComment(@RequestBody CreateCommentRequest request,@PathVariable long id) {
		CommentEntity savedComment = commentService.createComment(request, id);
		URI savedURI = URI.create("/comments/comment" + savedComment.getId());
		
		commentResponse = modelMapper.map(savedComment, CommentResponse.class);
		commentResponse.setStatusCode(HttpStatus.CREATED.value());
		commentResponse.setMessage("Comment Added");
		
		return ResponseEntity.ok(commentResponse);
	}
	
	@DeleteMapping("/{slug}/comments/{id}")
	public ResponseEntity<CommentResponse> deleteComment(@PathVariable String slug, @PathVariable long id) {
		ArticleEntity article = articlesService.getArticleBySlug(slug);
		CommentEntity commentToDelete = commentService.getCommentByArticleAndCommentId(id, article);
		UserEntity user = commentToDelete.getAuthor();
		List<CommentEntity> comments = user.getComments();
		for (CommentEntity comment : comments) {
			if (comment.getId() == commentToDelete.getId()) {
				commentService.deleteComment(comment.getId());
			}
		}
		commentService.deleteComment(commentToDelete.getId());
		
		commentResponse = modelMapper.map(commentToDelete, CommentResponse.class);
		commentResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
		commentResponse.setMessage("DELETE success");
		
		return ResponseEntity.ok(commentResponse);
	}
}
