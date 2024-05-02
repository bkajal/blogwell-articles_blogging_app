package in.blogs.blogwellapp.comments;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import in.blogs.blogwellapp.articles.ArticleEntity;
import in.blogs.blogwellapp.users.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity(name="comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(nullable = false)
	private long id;
	@Nullable
	private String title;
	@NonNull
	private String body;
	@CreatedDate
	private Date createdAt;
	@ManyToOne
	@JoinColumn(name="article_id", nullable = false)
	private ArticleEntity article;
	@ManyToOne
	@JoinColumn(name="author_id", nullable = false)
	private UserEntity author;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
}
