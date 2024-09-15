package Main.DTO;

import Main.model.MyMatchScore;
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
public class MyMatchCreationDTO{
    String name;
    List<String> teamA;
    List<String> teamB;
    Date startTimeMatch;
    Date endTimeMatch;
    String teamLeaderA;
    String teamLeaderB;
    MyMatchScore teamAScore;
    MyMatchScore teamBScore;
    String map;
}
