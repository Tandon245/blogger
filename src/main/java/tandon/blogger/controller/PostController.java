package tandon.blogger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tandon.blogger.dto.PostDTO;
import tandon.blogger.model.Post;
import tandon.blogger.repository.IPostRepository;
import tandon.blogger.repository.IUserRepository;
import tandon.blogger.service.PostService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPostRepository postRepository;


    @PostMapping("/addPost")
    public ResponseEntity<String> createPost(@RequestBody PostDTO postDTO) {
        Long userId = postDTO.getUserId();
        if (userRepository.findById(userId).isPresent()) {
            postService.createPost(postDTO);

            return new ResponseEntity<>(postDTO.getTitle() + "\n" + postDTO.getPostBody(), HttpStatus.CREATED);
        }

        return new ResponseEntity<>("No such user with UserId " + userId + " exists in Blogger", HttpStatus.NOT_FOUND);
    }


    @GetMapping("/getPostsByUserId/{userId}")
    public ResponseEntity<List<String>> getPostsByUserId(@PathVariable Long userId) {
        if (!userRepository.findById(userId).isPresent()) {
            return new ResponseEntity<>(List.of("User with userId " + userId + " doesn't exist"), HttpStatus.NOT_FOUND);
        }

        if (postService.getPostByUserId(userId) == null) {
            return new ResponseEntity<>(List.of("User with userId " + userId + " haven't posted any post"), HttpStatus.FOUND);
        }

        List<Post> allPosts = new ArrayList<>();
        allPosts = postService.getPostByUserId(userId);
        List<String> postBody = new ArrayList<>();
        for (Post post : allPosts) {
            postBody.add(post.getPostBody());
        }
        return new ResponseEntity<>(postBody, HttpStatus.FOUND);
    }

    @PutMapping("updatePost/{userId}/{postId}/{password}")
    public ResponseEntity<String> updatePost(@PathVariable Long userId, @PathVariable Long postId, @PathVariable String password, @RequestBody PostDTO postDTO) {
        if (userRepository.findById(userId).isPresent() && postRepository.findById(postId).isPresent()) {
            if (userRepository.findById(userId).get().getPassword().equals(password)) {
              Optional<Post> updatedPost=  postService.updatePost(userId, postId, postDTO);
                return new ResponseEntity<>("Post with post Id "+updatedPost.get().getPostId()+" updated\n"+updatedPost, HttpStatus.OK);
            }
            return new ResponseEntity<>("password didn't match", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("please enter valid details", HttpStatus.BAD_REQUEST);
    }

}