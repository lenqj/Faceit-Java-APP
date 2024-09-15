package Main.DTO;

import Main.model.MyMatchScore;
import Main.model.MyUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyMatchDTO{
    String name;
    List<MyUserInMatchDTO> teamA;
    List<MyUserInMatchDTO> teamB;
    Date startTimeMatch;
    Date endTimeMatch;
    MyUserDTO teamLeaderA;
    MyUserDTO teamLeaderB;
    MyMatchScore teamAScore;
    MyMatchScore teamBScore;
    String map;
}
