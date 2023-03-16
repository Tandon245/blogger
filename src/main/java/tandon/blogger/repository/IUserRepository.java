package tandon.blogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tandon.blogger.model.User;

public interface IUserRepository extends JpaRepository<User,Long> {
}
