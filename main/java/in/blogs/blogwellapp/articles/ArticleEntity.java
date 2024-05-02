package in.blogs.blogwellapp.articles;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

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

@Entity(name="articles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(nullable = false)
	private long id;
	@NonNull
	private String title;
	@NonNull
	@Column(unique = true)
	private String slug;
	@Nullable
	private String subtitle;
	@NonNull
	private String body;
	@CreatedDate
	private Date createdAt;
	@ManyToOne
	@JoinColumn(name="author_id", nullable = false)
	private UserEntity author;
	
}
