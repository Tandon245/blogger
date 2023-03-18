package tandon.blogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tandon.blogger.model.Post;
import tandon.blogger.model.User;
import  java.util.*;
public interface IUserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);


}
