package tandon.blogger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tandon.blogger.dto.PostDTO;
import tandon.blogger.model.Post;
import tandon.blogger.repository.IPostRepository;
import tandon.blogger.repository.IUserRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PostService {
    @Autowired
    private UserService userService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPostRepository postRepository;



    public Post createPost(PostDTO postDTO) {
        Post post = new Post();
        post.setUser(userService.getUserById(postDTO.getUserId()));
        post.setPostBody(postDTO.getPostBody());
        post.setTitle(postDTO.getTitle());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
        return post;
    }


    public List<Post> getPostByUserId(Long userId) {
     List<Post> allPosts=allPosts();
     Set<Post> existingPost=new HashSet<>();
     for(Post post:allPosts){
         if(post.getUser().getUserId()==userId){
             existingPost.add(post);
         }
     }
     if(existingPost.size()==0){
         return null;
     }
        return existingPost.stream().toList();
    }


    public List<Post> allPosts(){
        return  postRepository.findAll();
    }

    public Post updatePost(Long userId, Long postId, PostDTO postDTO) {
if(postRepository.findById(postId).isPresent() && userRepository.findById(userId).isPresent()) {

    Optional<Post> existingPost = postRepository.findById(postId);

    existingPost.get().setPostBody(postDTO.getPostBody());
    existingPost.get().setUpdatedAt(LocalDateTime.now());
    existingPost.get().setTitle(postDTO.getTitle());
    postRepository.save(existingPost.get());
    return existingPost.get();
}
return null;
    }


}