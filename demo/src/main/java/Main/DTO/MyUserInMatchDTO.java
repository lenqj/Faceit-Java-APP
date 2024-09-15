package Main.DTO;
import Main.model.MyMatch;
import Main.model.MyUser;
import Main.model.Score;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserInMatchDTO{
    private MyUserDTO myUser;
    private Score score;
}
