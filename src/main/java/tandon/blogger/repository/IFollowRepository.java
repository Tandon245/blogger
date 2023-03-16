package tandon.blogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tandon.blogger.model.Follow;
import java.util.*;

public interface IFollowRepository extends JpaRepository<Follow,Long> {


}
