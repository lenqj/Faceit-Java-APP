package Main.repository;

import Main.model.MyMatchScore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyMatchScoreRepository extends CrudRepository<MyMatchScore, Long> {
}
