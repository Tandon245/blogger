package tandon.blogger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tandon.blogger.model.Comment;
import tandon.blogger.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/addComment")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/getAllComments")
    public List<Comment> getAllComments() {
        return commentService.getComments();
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<String> getCommentById(@PathVariable Long commentId) {
        if (commentService.getCommentByCommentId(commentId) != null) {
            return new ResponseEntity<>(commentService.getCommentByCommentId(commentId).toString(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>("No Such Comment with commentId " + commentId + "exists", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteCommentByCommentId(@PathVariable Long commentId) {
        if (commentService.deleteCommentByCommentId(commentId) != null) {
            return new ResponseEntity<>("deleted successfully", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No such Comment with commentId " + commentId + " exists", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("updateComment/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId,@RequestBody Comment upComment){
        if(commentService.updateCommentByCommentId(commentId,upComment)!=null){
            commentService.updateCommentByCommentId(commentId,upComment);
            return  new ResponseEntity<>("updated successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("No such comment exists",HttpStatus.BAD_REQUEST);
    }
}
