package Main.repository;

import Main.model.MyMatch;
import Main.model.MyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyUserRepository extends CrudRepository<MyUser, Long> {
    Optional<MyUser> findMyUserByUsername(String username);
    Optional<MyUser> findMyUserByUsernameAndPassword(String username, String password);
    List<MyMatch> findAllMyMatchByUsername(String username);
    Page<MyUser> findAll(Pageable pageable);
    Page<MyUser> findAllByRoleName(Pageable pageable, String role);
    default Page<MyUser> findAllUsers(Pageable pageable, String role) {
        if (role == null || role.isEmpty() || role.equals("all")) {
            return findAll(pageable);
        } else {
            return findAllByRoleName(pageable, role);
        }
    }
}

