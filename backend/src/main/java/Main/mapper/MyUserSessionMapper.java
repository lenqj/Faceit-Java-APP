package Main.mapper;

import Main.DTO.MyUserDTO;
import Main.DTO.MyUserCreationDTO;
import Main.DTO.MyUserSessionDTO;
import Main.model.MyUser;
import Main.model.MyUserSession;
import Main.model.Score;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyUserSessionMapper {
    private final MyUserMapper myUserMapper;

    public MyUserSessionMapper(@Lazy MyUserMapper myUserMapper) {
        this.myUserMapper = myUserMapper;
    }

    public MyUserSessionDTO myUserSessionToMyUserSessionDTO(MyUserSession myUserSession){
        return MyUserSessionDTO.builder()
                .myUser(myUserMapper.myUserToMyUserDTO(myUserSession.getMyUser()))
                .loginAt(myUserSession.getLoginAt())
                .logoutAt(myUserSession.getLogoutAt())
                .build();
    }


    public Page<MyUserSessionDTO> myUserSessionsToMyUserSessionDTOS(Page<MyUserSession> myUserSessions) {
        List<MyUserSessionDTO> myUserSessionDTOList = myUserSessions.getContent().stream()
                .map(this::myUserSessionToMyUserSessionDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(myUserSessionDTOList, myUserSessions.getPageable(), myUserSessions.getTotalElements());
    }

    public List<MyUserSessionDTO> myUserSessionsToMyUserSessionDTOS(List<MyUserSession> myUserSessions) {
        return myUserSessions.stream()
                .map(this::myUserSessionToMyUserSessionDTO)
                .toList();
    }
    public MyUserSession myUserSessionDTOToMyUserSession(MyUserSessionDTO myUserSession){
        return MyUserSession.builder()
                .loginAt(myUserSession.getLoginAt())
                .logoutAt(myUserSession.getLogoutAt())
                .build();
    }
}

