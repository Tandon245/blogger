package tandon.blogger.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tandon.blogger.model.User;
import tandon.blogger.repository.IUserRepository;
import tandon.blogger.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private IUserRepository userRepository;


    @GetMapping("/{userId}")
    public ResponseEntity<String> getUser(@PathVariable Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            User user = userService.getUserById(userId);
            return new ResponseEntity<>(user.toString(), HttpStatus.OK);
        }

        return new ResponseEntity<>("user with userId " + userId + " doesn't exists in the Blogger", HttpStatus.NOT_FOUND);
    }



    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {


        if (userRepository.findByUserName(user.getUserName()) == null) {
            User savedUser = userService.createUser(user);
            return new ResponseEntity<>("User is added successfully with username-" + user.getUserName() + ",\nuserId-: " + savedUser.getUserId(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User with username " + user.getUserName() + " already exist", HttpStatus.BAD_REQUEST);
    }



    @DeleteMapping("/{userId}/{password}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId, @PathVariable String password) {
        if(userRepository.findById(userId).isPresent()) {
            if(userRepository.findById(userId).get().getPassword().equals(password)) {
                userService.deleteUser(userId);
                return new ResponseEntity<>("deleted successfully ",HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>("Wrong password",HttpStatus.FORBIDDEN);
        }
       return new ResponseEntity<>("user with userId "+userId+" doesn't exist",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<String>> getAllUserNames() {
        List<String> usernames = userService.getAllUsernames();
        return ResponseEntity.ok(usernames);
    }



    @GetMapping("userDetails/{userId}/{password}")
    public ResponseEntity<String> userDetails(@PathVariable Long userId, @PathVariable String password) {
        if (userRepository.findById(userId).isPresent()) {
            if (userRepository.findById(userId).get().getPassword().equals(password)) {
                return new ResponseEntity<>(userService.getUserDetails(userId).toString(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Wrong password", HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>("User with user Id -" + userId + " doesn't exist in Blogger", HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/{userId}/{password}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody User user,@PathVariable String password) {
        if (userRepository.findById(userId).isPresent()) {
            if (userId == user.getUserId()) {
                if(userRepository.findById(userId).get().getPassword().equals(password)){
                Optional<User> updatedUser = userService.updateUser(userId, user);
                return new ResponseEntity<>("user with userId" + updatedUser.get().getUserId() + " updated successfully", HttpStatus.OK);
                }
                return new ResponseEntity<>("Wrong password",HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("userId would be  same as created first time", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("user With UserId " + userId + " doesn't exist in the Blogger", HttpStatus.BAD_REQUEST);
    }
}
