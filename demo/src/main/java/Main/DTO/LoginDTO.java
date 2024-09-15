package Main.DTO;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private String username;
    private String password;
}
