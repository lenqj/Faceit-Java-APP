package Main.repository;

import Main.model.MyMatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public interface MyMatchRepository extends CrudRepository<MyMatch, Long> {
    Optional<MyMatch> findByName(@Param("name") String name);
    Page<MyMatch> findAll(Pageable pageable);
    Page<MyMatch> findAllByMap(Pageable pageable, String map);
    Page<MyMatch> findAllByTeamA_MyUserUsername(Pageable pageable, String username);
    Page<MyMatch> findAllByTeamB_MyUserUsername(Pageable pageable, String username);
    List<MyMatch> findAllListByTeamA_MyUserUsername(String username);
    List<MyMatch> findAllListByTeamB_MyUserUsername(String username);

    Page<MyMatch> findAllByMapAndTeamA_MyUserUsername(Pageable pageable, String username, String map);
    Page<MyMatch> findAllByMapAndTeamB_MyUserUsername(Pageable pageable, String username, String map);

    default Page<MyMatch> findAllMatches(Pageable pageable, String map, String username) {
        Optional<String> mapOptional = Optional.ofNullable(map) ;
        Optional<String> usernameOptional = Optional.ofNullable(username);
        boolean isAllMaps = mapOptional.isPresent() && mapOptional.get().equalsIgnoreCase("ALL");

        if (mapOptional.isPresent() && usernameOptional.isPresent() && !isAllMaps) {
            return findAllByTeamAOrTeamB_MyUserUsernameAndMap(pageable, username, map);
        } else if (mapOptional.isPresent() && !isAllMaps) {
            return findAllByMap(pageable, map);
        } else if (usernameOptional.isPresent()) {
            return findAllByTeamAOrTeamB_MyUserUsername(pageable, username);
        } else {
            return findAll(pageable);
        }
    }

    default Page<MyMatch> findAllByTeamAOrTeamB_MyUserUsernameAndMap(Pageable pageable, String username, String map) {
        Page<MyMatch> matchesForTeamA = findAllByTeamA_MyUserUsername(pageable, username);
        Page<MyMatch> matchesForTeamB = findAllByTeamB_MyUserUsername(pageable, username);
        Stream<MyMatch> combinedStream = Stream.concat(matchesForTeamA.stream().filter(match -> match.getMap().equals(map)), matchesForTeamB.stream().filter(match -> match.getMap().equals(map)));
        return PageableExecutionUtils.getPage(combinedStream.collect(Collectors.toList()), pageable,
                () -> matchesForTeamA.getTotalElements() + matchesForTeamB.getTotalElements());

    }

    default Page<MyMatch> findAllByTeamAOrTeamB_MyUserUsername(Pageable pageable, String username) {
        Page<MyMatch> matchesForTeamA = findAllByTeamA_MyUserUsername(pageable, username);
        Page<MyMatch> matchesForTeamB = findAllByTeamB_MyUserUsername(pageable, username);
        Stream<MyMatch> combinedStream = Stream.concat(matchesForTeamA.stream(), matchesForTeamB.stream());

        return PageableExecutionUtils.getPage(combinedStream.collect(Collectors.toList()), pageable,
                () -> matchesForTeamA.getTotalElements() + matchesForTeamB.getTotalElements());

    }

    default List<MyMatch> findAllListByTeamAOrTeamB_MyUserUsernameAndMap(String username) {
        List<MyMatch> matchesForTeamA = findAllListByTeamA_MyUserUsername(username);
        List<MyMatch> matchesForTeamB = findAllListByTeamB_MyUserUsername(username);
        List<MyMatch> fullList = new ArrayList<>();
        fullList.addAll(matchesForTeamA);
        fullList.addAll(matchesForTeamB);
        return fullList;

    }

}
