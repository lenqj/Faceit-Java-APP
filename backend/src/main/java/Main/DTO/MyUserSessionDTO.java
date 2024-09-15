package Main.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserSessionDTO{
    private MyUserDTO myUser;
    private LocalDateTime loginAt;
    private LocalDateTime logoutAt;
}
