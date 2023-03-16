package tandon.blogger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tandon.blogger.model.Comment;
import tandon.blogger.model.Post;
import tandon.blogger.repository.IPostRepository;
import tandon.blogger.repository.IUserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private IUserRepository userRepository;

    public Post findById(Long postId) {
        for (Post post : posts) {
            if (post.getPostId() == postId) {
                return post;
            }
        }
        return null;
    }

    public Post getPostById(Long postId) {

        return findById(postId);
    }

    List<Post> posts = new ArrayList<>();

    public List<Post> allPosts() {
        return posts;
    }

    public Post savePost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        posts.add(post);
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, Post post) {
        Post existingPost = findById(postId);
        if (existingPost != null) {
            existingPost.setTitle(post.getTitle());
            existingPost.setBody(post.getBody());
            existingPost.setUpdatedAt(LocalDateTime.now());
            return postRepository.save(existingPost);
        }
        return null;
    }

    public Post deletePostById(Long postId) {
        for (Post post:posts){
            if(post.getPostId()==postId){
                posts.remove(post);
                return post;
            }
        }
        return null;
    }

    public List<Comment> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            return post.getComments();
        }
        return null;
    }

    public Comment createCommentForPost(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            comment.setUser(comment.getUser());
            comment.setCommentBody(comment.getCommentBody());
            comment.setCreatedAt(LocalDateTime.now());
            comment.setUpdatedAt(LocalDateTime.now());
            return comment;
        }
        return null;
    }
}