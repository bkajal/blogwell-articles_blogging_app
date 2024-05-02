package in.blogs.blogwellapp.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {
	@Autowired
	private CommentsRepository commentsRepository;
}
