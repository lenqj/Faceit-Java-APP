package Main.repository;

import Main.model.EmailPayload;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailPayloadRepository extends CrudRepository<EmailPayload, Long> {
}
