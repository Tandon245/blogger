package tandon.blogger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tandon.blogger.model.User;
import tandon.blogger.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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



    public Optional<User> updateUser(Long userId, User user) {
        Optional<User> user1=userRepository.findById(user.getUserId());
        user1.get().setUserId(userId);
        user1.get().setPhoneNumber(user.getUserName());
        user1.get().setUserName(user.getUserName());
        user1.get().setDateOfBirth(user.getDateOfBirth());
        user1.get().setEmail(user.getEmail());
        user1.get().setGender(user.getGender());
        user1.get().setFirstName(user.getFirstName());
        user1.get().setLastName(user.getLastName());
        user1.get().setPassword(user.getPassword());
        userRepository.save(user);
        return user1;
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
        return null;
    }


    public User getUserDetails(Long userId){
        if(userRepository.findById(userId).isPresent()){
            return userRepository.findById(userId).get();
        }
       return null;
    }
}
