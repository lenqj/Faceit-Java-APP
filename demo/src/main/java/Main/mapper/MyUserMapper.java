package Main.mapper;

import Main.DTO.MyUserDTO;
import Main.DTO.MyUserCreationDTO;
import Main.model.MyUser;
import Main.model.Score;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyUserMapper {
    private final UserRoleMapper userRoleMapper;

    public MyUserMapper(@Lazy UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    public MyUserDTO myUserToMyUserDTO(MyUser user){
        return MyUserDTO.builder()
                .username(user.getUsername())
                .role(userRoleMapper.userRoleToUserRoleDTO(user.getRole()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .emailAddress(user.getEmailAddress())
                .score(user.getScore())
                .numberOfMatches(user.getNumberOfMatches())
                .build();
    }


    public Page<MyUserDTO> myUsersToMyUserDTOS(Page<MyUser> users) {
        List<MyUserDTO> userDTOList = users.getContent().stream()
                .map(this::myUserToMyUserDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(userDTOList, users.getPageable(), users.getTotalElements());
    }

    public List<MyUserDTO> myUsersToMyUserDTOS(List<MyUser> users) {
        return users.stream()
                .map(this::myUserToMyUserDTO)
                .collect(Collectors.toList());
    }

    public MyUser myUserForCreationToMyUser(MyUserCreationDTO myUserCreationDTO){
        return MyUser.builder()
                .username(myUserCreationDTO.getUsername())
                .password(myUserCreationDTO.getPassword())
                .firstName(myUserCreationDTO.getFirstName())
                .lastName(myUserCreationDTO.getLastName())
                .emailAddress(myUserCreationDTO.getEmailAddress())
                .numberOfMatches(myUserCreationDTO.getNumberOfMatches() != null ? myUserCreationDTO.getNumberOfMatches() : 0)
                .score(myUserCreationDTO.getScore() != null ?  myUserCreationDTO.getScore() : Score.builder()
                        .kills(0)
                        .assists(0)
                        .deaths(0)
                        .headshots(0)
                        .mvps(0)
                        .build())
                .build();
    }

}

