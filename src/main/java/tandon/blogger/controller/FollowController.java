package tandon.blogger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tandon.blogger.dto.FollowDTO;
import tandon.blogger.repository.IUserRepository;
import tandon.blogger.service.FollowService;

import java.util.List;


@RestController
@RequestMapping("/follows")
public class FollowController {

    @Autowired
    private FollowService followService;
    @Autowired
    private IUserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> addFollower(@RequestBody FollowDTO followDTO) {

        if (!(userRepository.findById(followDTO.getFollowingId()).isPresent() && userRepository.findById(followDTO.getFollowerId()).isPresent())) {
            return new ResponseEntity<>("Please enter valid user Id", HttpStatus.BAD_REQUEST);
        }

        if (followService.addFollower(followDTO) == null) {
            return new ResponseEntity<>("Can't follow user itself", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findById(followDTO.getFollowerId()) != null && userRepository.findById(followDTO.getFollowingId()) != null) {
            followService.addFollower(followDTO);
            return new ResponseEntity<>("userId-" + followDTO.getFollowerId() + " followed " + "userId-" + followDTO.getFollowingId(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("No such users exists", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<String>> getAllFollowers(@PathVariable Long userId) {

        if (userRepository.findById(userId).isPresent() && followService.getFollowers(userId) != null) {
            return new ResponseEntity<>(followService.getFollowers(userId), HttpStatus.OK);

        } else if (userRepository.findById(userId).isPresent() && followService.getFollowers(userId) == null) {
            return new ResponseEntity<>(List.of("No one is following this user"), HttpStatus.OK);
        }
        return new ResponseEntity<>(List.of("User with userId " + userId + " doesn't exist in Blogger"), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/followings/{userId}")
    public ResponseEntity<List<String>> getAllFollowing(@PathVariable Long userId) {
        if (userRepository.findById(userId).isPresent() && followService.getFollowings(userId) != null) {
            return new ResponseEntity<>(followService.getFollowings(userId), HttpStatus.OK);
        } else if (userRepository.findById(userId).isPresent() && followService.getFollowings(userId) == null) {
            return new ResponseEntity<>(List.of("User with userId " + userId + " doesn't following any user"), HttpStatus.OK);
        }
        return new ResponseEntity<>(List.of("User with userId " + userId + " doesn't exist in Blogger"), HttpStatus.NOT_FOUND);
    }


}
