package Main.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserTeamDTO {
    private String name;
    private List<MyUserDTO> teamMembers;
    private MyUserDTO teamLeader;
    private List<MyMatchDTO> matches;
    private Integer numberOfMatches;
}
