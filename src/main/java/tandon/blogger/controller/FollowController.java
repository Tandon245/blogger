package tandon.blogger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        if (userRepository.findById(followDTO.getFollowerId()) != null && userRepository.findById(followDTO.getFollowingId()) != null) {
            followService.addFollower(followDTO);
            return new ResponseEntity<>("userId-"+followDTO.getFollowerId() + " followed " +"userId-"+ followDTO.getFollowingId(), HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("No such users exists", HttpStatus.BAD_REQUEST);
        }
    }

    public List<String> getAllFollowerById
}
