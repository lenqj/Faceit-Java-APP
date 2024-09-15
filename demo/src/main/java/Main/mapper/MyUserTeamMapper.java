package Main.mapper;

import Main.DTO.MyUserTeamDTO;
import Main.model.MyTeam;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyUserTeamMapper {
    private final MyUserMapper myUserMapper;
    private final MyMatchMapper myMatchMapper;

    public MyUserTeamMapper(@Lazy MyUserMapper myUserMapper, @Lazy MyMatchMapper myMatchMapper) {
        this.myUserMapper = myUserMapper;
        this.myMatchMapper = myMatchMapper;
    }

    public MyUserTeamDTO myUserTeamToMyUserTeamDTO(MyTeam myTeam){
        return MyUserTeamDTO.builder()
                .name(myTeam.getName())
                .teamMembers(myUserMapper.myUsersToMyUserDTOS(myTeam.getTeamMembers()))
                .teamLeader(myUserMapper.myUserToMyUserDTO(myTeam.getTeamLeader()))
                .matches(myTeam.getMatches() != null ? myMatchMapper.myMatchesToMyMatchDTOS(myTeam.getMatches()) : new ArrayList<>())
                .numberOfMatches(myTeam.getNumberOfMatches())
                .build();
    }

    public Page<MyUserTeamDTO> myUserTeamsToMyUserTeamDTOS(Page<MyTeam> myUserTeams) {
        List<MyUserTeamDTO> myUserTeamDTOS = myUserTeams.getContent().stream()
                .map(this::myUserTeamToMyUserTeamDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(myUserTeamDTOS, myUserTeams.getPageable(), myUserTeams.getTotalElements());
    }

}

