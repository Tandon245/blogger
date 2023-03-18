package tandon.blogger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tandon.blogger.model.Post;
import tandon.blogger.model.User;
import tandon.blogger.repository.IUserRepository;
import tandon.blogger.service.PostService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private IUserRepository userRepository;

    @GetMapping("getPostByPostId/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/addPost")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        User user = userRepository.findById(post.getUser().getUserId()).orElse(null);
        post.setUser(user);
        Post savedPost = postService.createPost(post);

        return ResponseEntity.created(URI.create("/posts/" + savedPost.getPostId())).body(savedPost);
    }

    @PutMapping("/updatePostByPostId/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post post) {
        post.setPostId(postId);
        Post updatedPost = postService.updatePost(post);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("deletePostByPostId/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getPostsByUserId/{userId}")

    public List<Post> getPostsByUserId(@PathVariable Long userId) {

      return   postService.getPostByUserId(userId);
    }


}