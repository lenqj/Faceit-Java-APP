package Main.repository;

import Main.model.MyUser;
import Main.model.MyUserInMatch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserInMatchRepository extends CrudRepository<MyUserInMatch, Long> {
}
