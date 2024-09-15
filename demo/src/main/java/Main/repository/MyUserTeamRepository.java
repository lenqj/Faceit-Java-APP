package Main.repository;

import Main.model.MyTeam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserTeamRepository extends CrudRepository<MyTeam, Long> {
    Optional<MyTeam> findMyUserTeamByName(String name);
    Page<MyTeam> findAll(Pageable pageable);
    Page<MyTeam> findAllByTeamLeader_Username(Pageable pageable, String username);
    Page<MyTeam> findAllByTeamMembers_Username(Pageable pageable, String username);
    default Page<MyTeam> findAllTeams(Pageable pageable, String leaderUsername, String playerUsername) {
        if (leaderUsername == null || leaderUsername.isEmpty()) {
            if (playerUsername == null || playerUsername.isEmpty()) {
                return findAll(pageable);
            }else{
                return findAllByTeamMembers_Username(pageable, playerUsername);
            }
        } else {
            if (playerUsername == null || playerUsername.isEmpty()) {
                return findAll(pageable);
            }else{
                return findAllByTeamLeader_Username(pageable, playerUsername);
            }
        }
    }
}