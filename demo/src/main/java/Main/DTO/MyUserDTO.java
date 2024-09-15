package Main.DTO;

import Main.model.Score;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserDTO {
        @Pattern(regexp = "^[a-zA-Z0-9]+[a-zA-Z0-9_-]*$", message = "Username must start with a letter or digit and can contain only letters, digits, '-' and '_'")
        @NotBlank(message = "Username is required")
        String username;
        UserRoleDTO role;
        String firstName;
        String lastName;
        String emailAddress;
        Score score;
        Integer numberOfMatches;
}
