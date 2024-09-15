package Main.mapper;

import Main.DTO.MyUserInMatchDTO;
import Main.model.MyUserInMatch;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class MyUserInMatchMapper {
    private final MyUserMapper myUserMapper;

    public MyUserInMatchMapper(@Lazy MyUserMapper myUserMapper) {
        this.myUserMapper = myUserMapper;
    }

    public MyUserInMatchDTO myUserInMatchToMyUserInMatchDTO(MyUserInMatch user){
        return MyUserInMatchDTO.builder()
                .myUser(myUserMapper.myUserToMyUserDTO(user.getMyUser()))
                .score(user.getScore())
                .build();
    }
    public List<MyUserInMatchDTO> myUsersInMatchToMyUserInMatchDTOS(List<MyUserInMatch> myUsers) {
        return myUsers.stream()
                .map(this::myUserInMatchToMyUserInMatchDTO)
                .toList();
    }
}
