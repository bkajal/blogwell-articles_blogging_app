package in.blogs.blogwellapp.users;


import java.util.List;

import org.springframework.lang.Nullable;

import in.blogs.blogwellapp.articles.ArticleEntity;
import in.blogs.blogwellapp.comments.CommentEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity(name="users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(nullable = false)
	private long id;
	@Column(nullable = false)
	@NonNull
	private String username;
	@Column(nullable = false)
	@NonNull
	private String password;
	@Column(nullable = false)
	@NonNull
	private String email;
	@Column(nullable = true)
	@Nullable
	private String bio;
	@Column(nullable = true)
	@Nullable
	private String image;
	@OneToMany(mappedBy = "author")
	List<ArticleEntity> articles;
	@OneToMany(mappedBy = "author")
	List<CommentEntity> comments;
}
