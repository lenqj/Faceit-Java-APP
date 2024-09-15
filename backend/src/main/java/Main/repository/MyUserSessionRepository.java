package Main.repository;


import Main.model.MyTeam;
import Main.model.MyUserSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyUserSessionRepository extends CrudRepository<MyUserSession, Long> {
    List<MyUserSession> findMyUserSessionsByMyUserUsernameAndLogoutAtIsNull(String username);
    MyUserSession findMyUserSessionByMyUserUsernameAndLogoutAtIsNull(String username);
    Page<MyUserSession> findAll(Pageable pageable);
    Page<MyUserSession> findAllMyUserSessionByMyUserUsername(Pageable pageable, String username);
    default Page<MyUserSession> findAllUserSessions(Pageable pageable, String username) {
        if (username == null || username.isEmpty()) {
                return findAll(pageable);
        } else {
            return findAllMyUserSessionByMyUserUsername(pageable, username);
        }
    }
}
