package tandon.blogger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tandon.blogger.dto.FollowDTO;
import tandon.blogger.model.Follow;
import tandon.blogger.repository.IFollowRepository;
import tandon.blogger.repository.IUserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class FollowService {
    @Autowired
    private IFollowRepository followRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private UserService userService;

    private List<Follow> allFollows = new ArrayList<>();

    public Follow addFollower(FollowDTO followDTO) {
        Follow follow = new Follow();
       if(followDTO.getFollowingId()==followDTO.getFollowerId()){
           return null;
       }
        Long followerId = followDTO.getFollowerId();
        Long followingId = followDTO.getFollowingId();
        follow.setFollowerId(userRepository.findById(followerId).orElse(null));
        follow.setFollowingId(userRepository.findById(followingId).orElse(null));
        allFollows.add(follow);
        return followRepository.save(follow);
    }


    private List<String> followers = new ArrayList<>();
    private List<String> followings = new ArrayList<>();

    public List<String> getFollowers(Long userId) {
        Set<String> follower=new HashSet<>();
        for (Follow follow : allFollows) {
            if (follow.getFollowingId().getUserId() == userId) {
                follower.add(follow.getFollowerId().getUserName());
            }
        }
        if(follower.size()==0){
            return null;
        }
        return new ArrayList<>(follower);
    }

    public List<String> getFollowings(Long userId) {
        Set<String> following=new HashSet<>();
        for (Follow follow : allFollows) {
            if (follow.getFollowerId().getUserId() == userId) {
                following.add(follow.getFollowingId().getUserName());
            }
        }
        if(following.size()==0){
            return null;
        }
        return new ArrayList<>(following);
    }


}
