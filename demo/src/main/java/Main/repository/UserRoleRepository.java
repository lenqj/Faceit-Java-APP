package Main.repository;

import Main.model.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    UserRole findByName(@Param("name") String name);
    void deleteRoleByName(String name);
}
