package tandon.blogger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tandon.blogger.model.Follow;
import tandon.blogger.repository.IFollowRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class FollowService {

    @Autowired
    private IFollowRepository followRepository;
    List<Follow> follows = new ArrayList<>();

    public Follow addFollow(Follow follow) {
        follows.add(followRepository.save(follow));
        return followRepository.save(follow);
    }

    public Follow getFollowsByFollowId(Long followId) {
        for (Follow follow : follows) {
            if (follow.getFollowId() == followId) {
                return follow;
            }
        }
        return null;
    }

    public Follow deleteFollow(Long followId) {
        Follow existingFollow = getFollowsByFollowId(followId);
        for (Follow follow : follows) {
            if (existingFollow == follow) {
                follows.remove(follow);
                return follow;
            }
        }
        return null;
    }

public  List<Follow> allFollows(){
        return follows;
}

}
