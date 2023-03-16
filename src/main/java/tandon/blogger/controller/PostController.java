package tandon.blogger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tandon.blogger.model.Comment;
import tandon.blogger.model.Post;
import tandon.blogger.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("findAll-posts")
    public List<Post> getAllPosts() {
        return postService.allPosts();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<String> getPostById(@PathVariable Long postId) {
        if(postService.getPostById(postId)!=null){
            return new ResponseEntity<>(postService.getPostById(postId).toString(),HttpStatus.OK);
        }
        return new ResponseEntity<>("No Such post with postId "+postId+" exists",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("add-post")
    public Post createPost(@RequestBody Post post) {
        return postService.savePost(post);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable Long postId, @RequestBody Post post) {
        if(postService.updatePost(postId,post)!=null){
            postService.updatePost(postId,post);
            return new ResponseEntity<>("Post Updated Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("No such post exist with"+postId,HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        if(postService.deletePostById(postId)!=null){

        postService.deletePostById(postId);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No such post in the database",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{postId}/comments")
    public List<Comment> getAllCommentsForPost(@PathVariable Long postId) {
        return postService.getAllCommentsForPost(postId);
    }

    @PostMapping("/{postId}/comments")
    public Comment createCommentForPost(@PathVariable Long postId, @RequestBody Comment comment) {
        return postService.createCommentForPost(postId, comment);
    }
}