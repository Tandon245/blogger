package tandon.blogger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tandon.blogger.model.Comment;
import tandon.blogger.repository.ICommentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private ICommentRepository commentRepository;
    List<Comment> comments = new ArrayList<>();

    public Comment addComment(Comment comment) {
        comments.add(commentRepository.save(comment));
        return commentRepository.save(comment);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Comment getCommentByCommentId(Long commentId) {

        for (Comment comment : comments) {
            if (comment.getCommentId() == commentId) {
                return comment;
            }
        }
        return null;
    }

    public Comment deleteCommentByCommentId(Long commentId) {
        Comment existingComment = getCommentByCommentId(commentId);
        if (existingComment != null) {
            for (Comment comment : comments) {
                if (comment == existingComment) {
                    comments.remove(existingComment);
                }
            }
        }
        return null;
    }
    public  Comment updateCommentByCommentId(Long commentId,Comment upComment){
        Comment existingComment = getCommentByCommentId(commentId);
      if(existingComment!=null){
          existingComment.setCommentBody(upComment.getCommentBody());
          existingComment.setUpdatedAt(LocalDateTime.now());
          return existingComment;
      }
      return null;
    }


}
