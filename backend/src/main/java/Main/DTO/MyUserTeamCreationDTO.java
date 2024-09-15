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
public class MyUserTeamCreationDTO {
    private String name;
    private List<String> teamMembers;
    private String teamLeader;
    private List<String> matches;
}
