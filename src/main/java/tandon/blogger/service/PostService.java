package tandon.blogger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tandon.blogger.model.Follow;
import tandon.blogger.model.Post;
import tandon.blogger.model.User;
import tandon.blogger.repository.IPostRepository;
import tandon.blogger.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
   private UserService userService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPostRepository postRepository;
    List<Post> allPosts=new ArrayList<>();

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post createPost(Post post) {
        allPosts.add(post);
        return postRepository.save(post);
    }

    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public  List<Post> getPostByUserId(Long userId){
        List<Post> existingPosts=new ArrayList<>();
        for (Post post:allPosts){
            if(post.getUser().getUserId()==userId){
                existingPosts.add(post);
            }
        }
        return  existingPosts;
    }






}