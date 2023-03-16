package tandon.blogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tandon.blogger.model.Comment;

public interface ICommentRepository extends JpaRepository<Comment,Long> {
}
