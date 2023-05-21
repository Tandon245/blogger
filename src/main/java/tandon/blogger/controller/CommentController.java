package tandon.blogger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tandon.blogger.model.Comment;
import tandon.blogger.repository.ICommentRepository;
import tandon.blogger.repository.IPostRepository;
import tandon.blogger.repository.IUserRepository;
import tandon.blogger.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPostRepository postRepository;

    @PostMapping("/addComment/{userId}/{postId}/{comment}")
    public ResponseEntity<String> addComment(@PathVariable Long userId, @PathVariable Long postId, @PathVariable String comment) {
        if (userRepository.findById(userId).isPresent() && postRepository.findById(postId).isPresent()) {
           Comment addedComment= commentService.add(userId, postId, comment);
            return new ResponseEntity<>("Comment added\n", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/comments/{userId}/{postId}")
    public ResponseEntity<List<String>> allComments(@PathVariable Long userId, @PathVariable Long postId) {
        if(!postRepository.findById(postId).isPresent()){
            return new ResponseEntity<>(List.of("Please enter valid postId"),HttpStatus.BAD_REQUEST);
        }
        if(!userRepository.findById(userId).isPresent()){
            return new ResponseEntity<>(List.of("Enter valid userId"),HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findById(userId).isPresent() && postRepository.findById(postId).isPresent()) {

            if (commentService.comments(userId, postId) == null) {
                return new ResponseEntity<>(List.of("No comments yet"), HttpStatus.OK);
            }
            return new ResponseEntity<>(commentService.comments(userId,postId),HttpStatus.OK);
        }
        return new ResponseEntity<>(List.of("Please check credentials"),HttpStatus.BAD_REQUEST);
    }



}
