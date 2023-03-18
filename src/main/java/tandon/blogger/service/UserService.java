package tandon.blogger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tandon.blogger.model.User;
import tandon.blogger.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {



    @Autowired
    private IUserRepository userRepository;


    List<User> allUsers=new ArrayList<>();

    public User createUser(User user) {
        allUsers.add(user);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<String> getAllUsernames() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(User::getUserName)
                .collect(Collectors.toList());
    }



    //find by userName
    public User findByUserName(String userName){
        for (User user:allUsers){
            if(userName.equals(user.getUserName())){
                return  user;
            }
        }
        return  null;
    }


}
