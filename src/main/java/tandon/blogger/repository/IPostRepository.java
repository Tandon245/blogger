package tandon.blogger.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import tandon.blogger.model.Post;

import java.util.ArrayList;
import java.util.List;

public interface IPostRepository extends JpaRepository<Post,Long> {



    @Override
    <S extends Post> S save(S entity);

}
