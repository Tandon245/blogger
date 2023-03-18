package tandon.blogger.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tandon.blogger.model.User;
import tandon.blogger.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.created(URI.create("/users/" + savedUser.getUserId())).body(savedUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        user.setUserId(userId);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllUsernames() {
        List<String> usernames = userService.getAllUsernames();
        return ResponseEntity.ok(usernames);
    }


    @GetMapping("userName/{userName}")
    public  ResponseEntity<String > findByUserName(@PathVariable String userName){
        if(userService.findByUserName(userName)!=null){
            return new ResponseEntity<>(userService.findByUserName(userName).toString(),HttpStatus.FOUND);
        }
        return new ResponseEntity<>("No such User exists in the database",HttpStatus.NOT_FOUND);
    }


}
