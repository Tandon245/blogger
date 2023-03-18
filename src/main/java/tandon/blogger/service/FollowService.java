package tandon.blogger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tandon.blogger.dto.FollowDTO;
import tandon.blogger.model.Follow;
import tandon.blogger.model.User;
import tandon.blogger.repository.IFollowRepository;
import tandon.blogger.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class FollowService {
    @Autowired
    private IFollowRepository followRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private UserService userService;

    List<String> allFollowers = new ArrayList<>();
    List<String> allFollowing = new ArrayList<>();


    public Follow addFollower(FollowDTO followDTO) {
        Follow follow = new Follow();
        Long followerId = followDTO.getFollowingId();
        Long followingId = followDTO.getFollowingId();
        follow.setFollowerId(userRepository.findById(followingId).orElse(null));
        follow.setFollowingId(userRepository.findById(followerId).orElse(null));
        if (userRepository.findById(followingId).orElse(null) != null) {
            Optional<User> followingUser = userRepository.findById(followerId);
            Optional<User> followerUser = userRepository.findById(followingId);
            String follower =followerUser.get().getUserName();
            String following =followingUser.get().getUserName();
            allFollowers.add(follower);
            allFollowing.add(following);


        }
        return followRepository.save(follow);

    }

    public List<String> getAllFollowers(){
        return allFollowers;
    }

    public List<String> getAllFollowing(){
        return allFollowing;
    }


}
