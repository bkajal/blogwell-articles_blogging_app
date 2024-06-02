package in.blogs.blogwellapp.comments;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.blogs.blogwellapp.articles.ArticleEntity;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, Long>{

	Optional<CommentEntity> findByIdAndArticle(long commentId, ArticleEntity article);

}
