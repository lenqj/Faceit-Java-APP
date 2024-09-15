package Main.mapper;

import Main.DTO.MyMatchCreationDTO;
import Main.DTO.MyMatchDTO;
import Main.DTO.MyUserDTO;
import Main.model.MyMatch;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyMatchMapper {
    private final MyUserInMatchMapper myUserInMatchMapper;
    private final MyUserMapper myUserMapper;

    public MyMatchMapper(@Lazy MyUserInMatchMapper myUserInMatchMapper, MyUserMapper myUserMapper) {
        this.myUserInMatchMapper = myUserInMatchMapper;
        this.myUserMapper = myUserMapper;
    }

    public MyMatchDTO myMatchToMyMatchDTO(MyMatch myMatch){
        return MyMatchDTO.builder()
                .name(myMatch.getName())
                .teamA(myUserInMatchMapper.myUsersInMatchToMyUserInMatchDTOS(myMatch.getTeamA()))
                .teamB(myUserInMatchMapper.myUsersInMatchToMyUserInMatchDTOS(myMatch.getTeamB()))
                .startTimeMatch(myMatch.getStartTimeMatch())
                .endTimeMatch(myMatch.getEndTimeMatch())
                .teamLeaderA(myUserMapper.myUserToMyUserDTO(myMatch.getTeamLeaderA()))
                .teamLeaderB(myUserMapper.myUserToMyUserDTO(myMatch.getTeamLeaderB()))
                .teamAScore(myMatch.getTeamAScore())
                .teamBScore(myMatch.getTeamBScore())
                .map(myMatch.getMap())
                .build();
    }
    public Page<MyMatchDTO> myMatchesToMyMatchDTOS(Page<MyMatch> myMatches){
        List<MyMatchDTO> myMatchDTOList = myMatches.getContent().stream()
                .map(this::myMatchToMyMatchDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(myMatchDTOList, myMatches.getPageable(), myMatches.getTotalElements());
    }
    public List<MyMatchDTO> myMatchesToMyMatchDTOS(List<MyMatch> myMatches){
        return myMatches.stream()
                .map(this::myMatchToMyMatchDTO)
                .collect(Collectors.toList());
    }

    public MyMatch myMatchDTOToMyMatch(MyMatchDTO myMatch){
        return MyMatch.builder()
                .name(myMatch.getName())
                .startTimeMatch(myMatch.getStartTimeMatch())
                .endTimeMatch(myMatch.getEndTimeMatch())
                .teamAScore(myMatch.getTeamAScore())
                .teamBScore(myMatch.getTeamBScore())
                .map(myMatch.getMap())
                .build();
    }
    public MyMatch myMatchDTOToMyMatch(MyMatchCreationDTO myMatch){
        return MyMatch.builder()
                .name(myMatch.getName())
                .startTimeMatch(myMatch.getStartTimeMatch())
                .endTimeMatch(myMatch.getEndTimeMatch())
                .teamAScore(myMatch.getTeamAScore())
                .teamBScore(myMatch.getTeamBScore())
                .map(myMatch.getMap())
                .build();
    }

}
