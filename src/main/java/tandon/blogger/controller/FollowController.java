package tandon.blogger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tandon.blogger.model.Follow;
import tandon.blogger.service.FollowService;

import java.util.List;


@RestController
@RequestMapping("/follows")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/addFollow")
    public ResponseEntity<String> addFollow(@RequestBody Follow follow) {
        followService.addFollow(follow);
        return new ResponseEntity<>(follow.toString(), HttpStatus.CREATED);
    }

    @GetMapping("/{followId}")
    public ResponseEntity<String> getFollowsByFollowId(@PathVariable Long followId) {
        if (followService.getFollowsByFollowId(followId) != null) {
            return new ResponseEntity<>(followService.getFollowsByFollowId(followId).toString(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>("No Such Follow Exists", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{followId}")
    public ResponseEntity<String> deleteByFollowId(@PathVariable Long followId) {
        if (followService.deleteFollow(followId) != null) {
            followService.deleteFollow(followId);
            return new ResponseEntity<>("Deleted SuccessFully", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No Such Follow Exists", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/getAllFollows")
    public List<Follow> getAllFollows(){
        return followService.allFollows();
    }

}
