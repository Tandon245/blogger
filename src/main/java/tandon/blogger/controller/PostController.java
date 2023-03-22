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
import tandon.blogger.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private UserService userService;


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
    public ResponseEntity<List<Map<String, String>>> getPostsByUserId(@PathVariable Long userId) {
        if (!userRepository.findById(userId).isPresent()) {
            return new ResponseEntity<>(List.of(Map.of("message", "User with userId " + userId + " doesn't exist")), HttpStatus.NOT_FOUND);
        }
        List<Post> posts = postService.getPostByUserId(userId);
        if (posts == null) {
            return new ResponseEntity<>(List.of(Map.of("message", "User with userId " + userId + " haven't posted any post")), HttpStatus.OK);
        }
        List<Map<String, String>> postDetails = new ArrayList<>();
        for (Post post : posts) {
            Map<String, String> details = new HashMap<>();
            details.put("postId", String.valueOf(post.getPostId()));
            details.put("postBody", post.getPostBody());
            postDetails.add(details);
        }
        return new ResponseEntity<>(postDetails, HttpStatus.OK);
    }


    @PutMapping("updatePost/{userId}/{postId}/{password}")
    public ResponseEntity<String> updatePost(@PathVariable Long userId, @PathVariable Long postId, @PathVariable String password, @RequestBody PostDTO postDTO) {
        if (userRepository.findById(userId).isPresent() && postRepository.findById(postId).isPresent() && postDTO.getUserId() == userId) {
            if (userRepository.findById(userId).get().getPassword().equals(password)) {


                Post existingPost = postService.updatePost(userId, postId, postDTO);
                return new ResponseEntity<>("Post  updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Wrong Password", HttpStatus.FORBIDDEN);
            }

        }
        return new ResponseEntity<>("Invalid credentials ", HttpStatus.BAD_REQUEST);
    }

}